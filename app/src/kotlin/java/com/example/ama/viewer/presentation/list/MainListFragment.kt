package com.example.ama.viewer.presentation.list

import android.os.Bundle
import android.view.*
import android.widget.TextView
import com.example.ama.viewer.R
import com.example.ama.viewer.data.repo.DataRepositoryImpl
import com.example.ama.viewer.presentation.list.mvp.MainListPresenterImpl
import com.example.ama.viewer.presentation.list.mvp.base.MainPresenter
import com.example.ama.viewer.presentation.list.mvp.base.MainView
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState
import kotlinx.android.synthetic.main.fragment_main.*

class MainListFragment : MvpLceViewStateFragment<TextView, String, MainView, MainPresenter>(), MainView {

    companion object {
        fun newInstance() = MainListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        srl_layout.setOnRefreshListener { loadData(true) }
        super.onViewCreated(view, savedInstanceState)
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
            MainListPresenterImpl(DataRepositoryImpl())

    override fun createViewState(): LceViewState<String, MainView> =
            RetainingLceViewState<String, MainView>()

    override fun setData(data: String?) {
        if (data != null) {
            contentView.text = data
        }
    }

    override fun getData() = contentView.text.toString()

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