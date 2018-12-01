package com.example.ama.viewer.presentation.list

import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.*
import com.example.ama.viewer.R
import com.example.ama.viewer.ViewerApp
import com.example.ama.viewer.data.model.GithubUser
import com.example.ama.viewer.data.repo.DataRepositoryImpl
import com.example.ama.viewer.presentation.list.mvp.MainListPresenterImpl
import com.example.ama.viewer.presentation.list.mvp.base.MainPresenter
import com.example.ama.viewer.presentation.list.mvp.base.MainView
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*

class MainListFragment : MvpLceViewStateFragment<CardView, GithubUser, MainView, MainPresenter>(), MainView {

    private var githubUser: GithubUser? = null

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
        tv_login.text = data.login
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