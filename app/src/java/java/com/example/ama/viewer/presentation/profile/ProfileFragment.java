package com.example.ama.viewer.presentation.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ama.viewer.R;
import com.example.ama.viewer.ViewerApp;
import com.example.ama.viewer.data.loader.ImageLoader;
import com.example.ama.viewer.data.loader.PicassoImageLoader;
import com.example.ama.viewer.data.model.GithubUser;
import com.example.ama.viewer.data.repo.DataRepositoryImpl;
import com.example.ama.viewer.presentation.profile.mvp.MainListPresenterImpl;
import com.example.ama.viewer.presentation.profile.mvp.base.MainPresenter;
import com.example.ama.viewer.presentation.profile.mvp.base.MainView;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProfileFragment extends MvpLceViewStateFragment<CardView, GithubUser, MainView, MainPresenter>
        implements MainView {

    private Unbinder unbinder;
    private ImageLoader<ImageView> imageLoader;

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.srl_layout)
    SwipeRefreshLayout srlLayout;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_content_name)
    TextView tvName;
    @BindView(R.id.tv_content_company)
    TextView tvCompany;
    @BindView(R.id.tv_content_location)
    TextView tvLocation;
    @BindView(R.id.tv_content_email)
    TextView tvEmail;
    @BindView(R.id.tv_content_blog)
    TextView tvSite;
    @BindView(R.id.tv_content_bio)
    TextView tvBio;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        imageLoader = new PicassoImageLoader(new Picasso.Builder(ViewerApp.getInstance()).build());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public GithubUser getData() {
        return new GithubUser();
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
    public LceViewState<GithubUser, MainView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        if (pullToRefresh) srlLayout.setRefreshing(false);
        String message = e.getMessage();
        return message == null ? getResources().getString(R.string.unknown_error) : message;
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
    public void setData(GithubUser data) {
        if (data.getAvatar() != null) {
            imageLoader.loadImage(data.getAvatar(), ivAvatar);
        }
        tvLogin.setText(data.getLogin());
        tvName.setText(data.getName());
        tvCompany.setText(data.getCompany());
        tvLocation.setText(data.getLocation());
        tvEmail.setText(data.getEmail());
        tvSite.setText(data.getBlog());
        tvBio.setText(data.getBio());
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
