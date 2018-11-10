package com.example.ama.viewer.presentation.main

import android.os.Bundle
import android.view.*
import android.widget.TextView
import com.example.ama.viewer.R
import com.example.ama.viewer.data.repo.DataRepositoryImpl
import com.example.ama.viewer.presentation.main.mvp.MainPresenterImpl
import com.example.ama.viewer.presentation.main.mvp.base.MainPresenter
import com.example.ama.viewer.presentation.main.mvp.base.MainView
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : MvpLceViewStateFragment<TextView, String, MainView, MainPresenter>(), MainView {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
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
        if (item?.itemId == R.id.mi_refresh) {
            loadData(false)
            return true
        }
        return false
    }

    override fun loadData(pullToRefresh: Boolean) {
        presenter.loadData(pullToRefresh)
    }

    override fun createPresenter(): MainPresenter =
            MainPresenterImpl(DataRepositoryImpl())

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
        return resources.getString(R.string.unknown_error)
    }

    override fun showContent() {
        super.showContent()
        srl_layout.isRefreshing = false
    }
}