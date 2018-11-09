package com.example.ama.viewer.presentation.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ama.viewer.R;
import com.example.ama.viewer.data.repo.DataRepositoryImpl;
import com.example.ama.viewer.presentation.main.mvp.MainPresenterImpl;
import com.example.ama.viewer.presentation.main.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.main.mvp.base.MainView;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;

public class MainFragment
        extends MvpLceViewStateFragment<TextView, String, MainView, MainPresenter>
        implements MainView {

    private TextView contentView;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        contentView = view.findViewById(R.id.contentView);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public String getData() {
        return contentView.getText().toString();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenterImpl(new DataRepositoryImpl());
    }

    @NonNull
    @Override
    public LceViewState<String, MainView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        String message = e.getMessage();
        return message != null ? message : getResources().getString(R.string.unknown_error);
    }

    @Override
    public void setData(String data) {
        contentView.setText(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadData();
    }
}
