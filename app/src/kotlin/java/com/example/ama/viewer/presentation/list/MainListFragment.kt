package com.example.ama.viewer.presentation.list

import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.*
import android.widget.ImageView
import com.example.ama.viewer.R
import com.example.ama.viewer.ViewerApp
import com.example.ama.viewer.data.loader.ImageLoader
import com.example.ama.viewer.data.loader.PicassoImageLoader
import com.example.ama.viewer.data.model.GithubUser
import com.example.ama.viewer.data.repo.DataRepositoryImpl
import com.example.ama.viewer.presentation.list.mvp.MainListPresenterImpl
import com.example.ama.viewer.presentation.list.mvp.base.MainPresenter
import com.example.ama.viewer.presentation.list.mvp.base.MainView
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*

class MainListFragment : MvpLceViewStateFragment<CardView, GithubUser, MainView, MainPresenter>(), MainView {

    private var githubUser: GithubUser? = null
    private val imageLoader: ImageLoader<ImageView> = PicassoImageLoader(
            Picasso.Builder(ViewerApp.instance).build())

    companion object {
        fun newInstance() = MainListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srl_layout.setOnRefreshListener { loadData(true) }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.mi_refresh && !viewState.isLoadingState) {
            loadData(false)
            return true
        }
        return false
    }

    override fun loadData(pullToRefresh: Boolean) {
        presenter.loadData(pullToRefresh)
    }

    override fun createPresenter(): MainPresenter =
            MainListPresenterImpl(
                    DataRepositoryImpl(ViewerApp.instance.githubApi),
                    AndroidSchedulers.mainThread())

    override fun createViewState(): LceViewState<GithubUser, MainView> =
            RetainingLceViewState<GithubUser, MainView>()

    override fun setData(data: GithubUser) {
        this.githubUser = data
        if (data.avatar != null) {
            imageLoader.loadImage(data.avatar, iv_avatar)
        }
        tv_login.text = data.login
        tv_content_name.text = data.name
        tv_content_blog.text = data.blog
        tv_content_company.text = data.company
        tv_content_email.text = data.email
        tv_content_location.text = data.location
        tv_content_bio.text = data.bio
    }

    override fun getData() = githubUser

    override fun getErrorMessage(e: Throwable?, pullToRefresh: Boolean): String {
        if (pullToRefresh) srl_layout.isRefreshing = false
        return e?.message ?: resources.getString(R.string.unknown_error)
    }

    override fun showContent() {
        srl_layout.isRefreshing = false
        super.showContent()
    }

    override fun animateErrorViewIn() {
        contentView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        srl_layout.visibility = View.VISIBLE
    }

    override fun animateContentViewIn() {
        contentView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
        loadingView.visibility = View.GONE
        srl_layout.visibility = View.VISIBLE
    }

    override fun animateLoadingViewIn() {
        super.animateLoadingViewIn()
        srl_layout.visibility = View.GONE
    }
}