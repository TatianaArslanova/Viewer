package com.example.ama.viewer.presentation.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ama.viewer.R;
import com.example.ama.viewer.data.repo.DataRepositoryImpl;
import com.example.ama.viewer.presentation.list.adapter.MainListAdapter;
import com.example.ama.viewer.presentation.list.mvp.MainListPresenterImpl;
import com.example.ama.viewer.presentation.list.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.list.mvp.base.MainView;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainListFragment
        extends MvpLceViewStateFragment<RecyclerView, List<String>, MainView, MainPresenter>
        implements MainView {

    private SwipeRefreshLayout srlLayout;
    private MainListAdapter adapter;

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
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        setListeners();
    }

    private void initUi(View view) {
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MainListAdapter();
        contentView.setAdapter(adapter);
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
    public List<String> getData() {
        return adapter.getItems();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainListPresenterImpl(
                new DataRepositoryImpl(),
                AndroidSchedulers.mainThread());
    }

    @NonNull
    @Override
    public LceViewState<List<String>, MainView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        if (pullToRefresh) srlLayout.setRefreshing(false);
        String message = e.getMessage();
        return message != null ? message : getResources().getString(R.string.unknown_error);
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
    public void setData(List<String> data) {
        adapter.setItems(data);
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

    @Override
    public void appendItemToList(String item) {
        adapter.appendItem(item);
    }
}
