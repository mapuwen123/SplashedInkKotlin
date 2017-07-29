package com.marvin.splashedinkkotlin.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseActivity
import com.marvin.splashedinkkotlin.bean.PhotoBean
import com.marvin.splashedinkkotlin.ui.download.DownloadActivity
import com.marvin.splashedinkkotlin.ui.particulars.ParticularsActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView,
        BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener,
        Toolbar.OnMenuItemClickListener {

    private var data: MutableList<PhotoBean> = ArrayList()
    private val adapter: MainAdapter = MainAdapter(this, R.layout.main_item, data)

    private var page = 1
    private val per_page = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun actionbarInit() {
        setSupportActionBar(toolbar as Toolbar?)

        (toolbar as Toolbar?)?.setOnMenuItemClickListener(this)
    }

    override fun dataInit() {
        adapter.setOnLoadMoreListener(this)
        adapter.onItemClickListener = this
        adapter.openLoadAnimation()

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        presenter.getPhotos(page, per_page)
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

    override fun upData(data: MutableList<PhotoBean>) {
        if (data.size != 0) {
            if (page == 1) {
                this.data.clear()
                this.data.addAll(data)
                adapter.notifyDataSetChanged()
            } else {
                adapter.addData(data)
            }
            if (data.size < 20) {
                adapter.loadMoreEnd()
            } else {
                adapter.loadMoreComplete()
            }
        } else {
            adapter.loadMoreEnd()
        }
    }

    override fun onLoadMoreRequested() {
        page = ++page
        presenter.getPhotos(page, per_page)
    }

    override fun onRefresh() {
        page = 1
        presenter.getPhotos(page, per_page)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val options = ActivityOptions.makeSceneTransitionAnimation(this, view, "image")
        val intent = Intent(this, ParticularsActivity::class.java)
        intent.putExtra("PHOTO_ID", data.get(position).id)
        intent.putExtra("IMAGE_URL", data.get(position).urls?.regular)
        intent.putExtra("HEIGHT", view?.height)
        startActivity(intent, options.toBundle())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        when (p0?.itemId) {
            R.id.drawload_item -> {
                startActivity(Intent(this, DownloadActivity::class.java))
            }
        }
        return true
    }
}
