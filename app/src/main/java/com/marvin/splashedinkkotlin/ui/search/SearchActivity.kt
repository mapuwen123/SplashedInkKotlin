package com.marvin.splashedinkkotlin.ui.search

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseActivity
import com.marvin.splashedinkkotlin.bean.SearchBean
import com.marvin.splashedinkkotlin.db.DatabaseUtils
import com.marvin.splashedinkkotlin.ui.particulars.ParticularsActivity
import com.marvin.splashedinkkotlin.ui.search.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.toast
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_search.view.*


class SearchActivity : BaseActivity<SearchView, SearchPresenter>(), SearchView,
        Toolbar.OnMenuItemClickListener,
        TextView.OnEditorActionListener,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {
    private val data: MutableList<SearchBean.ResultsBean> = ArrayList()

    private var adapter: SearchAdapter? = null

    private var page = 1

    private val per_page = 20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initPresenter(): SearchPresenter {
        return SearchPresenter()
    }

    override fun actionbarInit() {
        setSupportActionBar(toolbar)
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.setOnMenuItemClickListener(this)
    }

    override fun dataInit() {
        edit_search.clearFocus()
        val history_search_adapter = ArrayAdapter<String>(this, R.layout.search_item, DatabaseUtils.select_history_search(this))
        edit_search.setAdapter(history_search_adapter)
        edit_search.setOnEditorActionListener(this)

        swipe.setOnRefreshListener(this)

        adapter = SearchAdapter(this, R.layout.main_item, data)
        adapter?.setOnLoadMoreListener(this, recycler)
        adapter?.onItemClickListener = this
        adapter?.openLoadAnimation()

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    override fun showProgress() {
        swipe.isRefreshing = true
    }

    override fun hideProgress() {
        swipe.isRefreshing = false
    }

    override fun error(err: String) {
        toast(err)
    }

    override fun success(msg: String) {
        toast(msg)
    }

    override fun upData(data: SearchBean) {
        if (data.results?.size != 0) {
            if (page == 1) {
                this.data.clear()
                data.results?.let { this.data.addAll(it) }
                adapter?.notifyDataSetChanged()
            } else {
                data.results?.let { adapter?.addData(it) }
            }
            if (data.results?.size!! < 20) {
                adapter?.loadMoreEnd()
            } else {
                adapter?.loadMoreComplete()
            }
        } else {
            adapter?.loadMoreEnd()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.clear_item -> {
                edit_search.setText("")
            }
        }
        return true
    }

    override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
        if (p1 == EditorInfo.IME_ACTION_SEARCH) {
            DatabaseUtils.insert_history_search(this, edit_search.text.toString())
            page = 1
            presenter.doSearch(edit_search.text.toString(), page, per_page)
            edit_search.dismissDropDown()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager //得到InputMethodManager的实例
            if (imm.isActive()) {//如果开启
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
            }
            return true
        }
        return false
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val options = ActivityOptions.makeSceneTransitionAnimation(this, view, "image")
        val intent = Intent(this, ParticularsActivity::class.java)
        intent.putExtra("PHOTO_ID", data.get(position).id)
        intent.putExtra("IMAGE_URL", data.get(position).urls?.regular)
        intent.putExtra("HEIGHT", view?.height)
        startActivity(intent, options.toBundle())
    }

    override fun onLoadMoreRequested() {
        page = ++page
        presenter.doSearch(edit_search.text.toString(), page, per_page)
    }

    override fun onRefresh() {
        page = 1
        presenter.doSearch(edit_search.text.toString(), page, per_page)
    }
}
