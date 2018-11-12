package com.example.ama.viewer.presentation.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ama.viewer.R;
import com.example.ama.viewer.data.repo.DataRepositoryImpl;
import com.example.ama.viewer.presentation.list.mvp.MainListPresenterImpl;
import com.example.ama.viewer.presentation.list.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.list.mvp.base.MainView;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;

public class MainListFragment
        extends MvpLceViewStateFragment<TextView, String, MainView, MainPresenter>
        implements MainView {

    private TextView contentView;
    private TextView errorView;
    private ProgressBar loadingView;
    private SwipeRefreshLayout srlLayout;

    public static MainListFragment newInstance() {
        return new MainListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initUi(view);
        setListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initUi(View view) {
        contentView = view.findViewById(R.id.contentView);
        errorView = view.findViewById(R.id.errorView);
        loadingView = view.findViewById(R.id.loadingView);
        srlLayout = view.findViewById(R.id.srl_layout);
    }

    private void setListeners() {
        srlLayout.setOnRefreshListener(() -> loadData(true));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_refresh && !viewState.isLoadingState()) {
            loadData(false);
            return true;
        }
        return false;
    }

    @Override
    public String getData() {
        return contentView.getText().toString();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainListPresenterImpl(new DataRepositoryImpl());
    }

    @NonNull
    @Override
    public LceViewState<String, MainView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        if (pullToRefresh) srlLayout.setRefreshing(false);
        String message = e.getMessage();
        return message != null ? message : getResources().getString(R.string.unknown_error);
    }

    @Override
    public void setData(String data) {
        contentView.setText(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadData(pullToRefresh);
    }

    @Override
    public void showContent() {
        srlLayout.setRefreshing(false);
        super.showContent();
    }

    @Override
    protected void animateErrorViewIn() {
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        srlLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void animateContentViewIn() {
        contentView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        srlLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void animateLoadingViewIn() {
        super.animateLoadingViewIn();
        srlLayout.setVisibility(View.GONE);
    }
}
