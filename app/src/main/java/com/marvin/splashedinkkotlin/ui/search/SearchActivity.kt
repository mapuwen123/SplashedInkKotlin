package com.marvin.splashedinkkotlin.ui.search

import android.os.Bundle
import android.view.Menu

import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity<SearchView, SearchPresenter>(), SearchView {
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
    }

    override fun dataInit() {
        Logger.d("dataInit")
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error(err: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun success(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }
}
