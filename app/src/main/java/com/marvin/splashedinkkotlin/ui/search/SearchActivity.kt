package com.marvin.splashedinkkotlin.ui.search

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseActivity
import com.marvin.splashedinkkotlin.bean.SearchBean
import com.marvin.splashedinkkotlin.db.AppDataBase
import com.marvin.splashedinkkotlin.db.entity.SearchHisEntity
import com.marvin.splashedinkkotlin.ui.particulars.ParticularsActivity
import com.marvin.splashedinkkotlin.ui.search.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlin.concurrent.thread


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

    private var tv_search_null: TextView? = null

    private var imm: InputMethodManager? = null

    private lateinit var history_search_adapter: ArrayAdapter<String>
    private val hisList = mutableListOf<String>()

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initPresenter(): SearchPresenter = SearchPresenter()

    override fun actionbarInit() {
        setSupportActionBar(toolbar)
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.setOnMenuItemClickListener(this)
    }

    override fun dataInit() {
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager //得到InputMethodManager的实例

        edit_search.clearFocus()

        history_search_adapter = ArrayAdapter<String>(this, R.layout.search_item, hisList)
        edit_search.setAdapter(history_search_adapter)
        edit_search.setOnEditorActionListener(this)

        thread {
            hisList.clear()
            hisList.addAll(AppDataBase.db.searchHisDao().queryAll())
            runOnUiThread {
                history_search_adapter.notifyDataSetChanged()
            }
        }

        swipe.setOnRefreshListener(this)

        adapter = SearchAdapter(this, R.layout.main_item, data)
        val empty = layoutInflater.inflate(R.layout.search_null_init, null)
        tv_search_null = empty.findViewById(R.id.tv_search_null)
        tv_search_null?.text = getString(R.string.search_null_init)
        adapter?.emptyView = empty
        adapter?.setOnLoadMoreListener(this, recycler)
        adapter?.onItemClickListener = this
        adapter?.openLoadAnimation()

        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    override fun showProgress() {
        swipe.isRefreshing = true
    }

    override fun hideProgress() {
        swipe.isRefreshing = false
    }

    override fun error(err: String) {
        this.data.clear()
        adapter?.notifyDataSetChanged()
        tv_search_null?.text = err
    }

    override fun success(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun upData(data: SearchBean) {
        if (page == 1) {
            if (data.results?.size != 0) {
                this.data.clear()
                data.results?.let { this.data.addAll(it) }
                adapter?.notifyDataSetChanged()
            } else {
                this.data.clear()
                adapter?.notifyDataSetChanged()
                tv_search_null?.text = getString(R.string.search_null_callback)
            }
        } else {
            if (data.results?.size != 0) {
                data.results?.let { adapter?.addData(it) }
                if (data.results?.size!! < 20) {
                    adapter?.loadMoreEnd()
                } else {
                    adapter?.loadMoreComplete()
                }
            } else {
                adapter?.loadMoreEnd()
            }
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
//            DatabaseUtils.insert_history_search(this, edit_search.text.toString())
            thread {
                AppDataBase.db.searchHisDao().insert(SearchHisEntity(searchText = edit_search.text.toString()))
                hisList.clear()
                hisList.addAll(AppDataBase.db.searchHisDao().queryAll())
                runOnUiThread {
                    history_search_adapter.notifyDataSetChanged()
                }
            }
            page = 1
            presenter.doSearch(edit_search.text.toString(), page, per_page)
            edit_search.dismissDropDown()
            if (imm?.isActive()!!) {//如果开启
                imm?.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
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
