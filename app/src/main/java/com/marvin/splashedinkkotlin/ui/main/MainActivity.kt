package com.marvin.splashedinkkotlin.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseActivity
import com.marvin.splashedinkkotlin.ui.about.AboutActivity
import com.marvin.splashedinkkotlin.ui.download.DownloadActivity
import com.marvin.splashedinkkotlin.ui.main.adapter.PagerAdapter
import com.marvin.splashedinkkotlin.ui.main.fragment.LatestFragment
import com.marvin.splashedinkkotlin.ui.main.fragment.OldestFragment
import com.marvin.splashedinkkotlin.ui.main.fragment.PopularFragment
import com.marvin.splashedinkkotlin.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_pager_main.*
import kotlinx.android.synthetic.main.drawer_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView,
        NavigationView.OnNavigationItemSelectedListener,
        LatestFragment.OnFragmentInteractionListener,
        OldestFragment.OnFragmentInteractionListener,
        PopularFragment.OnFragmentInteractionListener,
        Toolbar.OnMenuItemClickListener {
    private var mDrawerToggle: ActionBarDrawerToggle? = null

    private val tabs: MutableList<String> = ArrayList()

    private val fragments: MutableList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        mDrawerToggle?.syncState()
        super.onPostCreate(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return mDrawerToggle?.onOptionsItemSelected(item)!! || super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun getLayoutId(): Int {
        return R.layout.drawer_main
    }

    override fun initPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun actionbarInit() {
        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        supportActionBar?.title = getString(R.string.app_name)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mDrawerToggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer.addDrawerListener(mDrawerToggle as ActionBarDrawerToggle)
        toolbar.setNavigationOnClickListener { drawer.openDrawer(Gravity.LEFT) }
        toolbar.setOnMenuItemClickListener(this)
    }

    override fun dataInit() {
        tabs.add("最新")
        tabs.add("最旧")
        tabs.add("最热")
        fragments.add(LatestFragment.newInstance(tabs[0]))
        fragments.add(OldestFragment.newInstance(tabs[1]))
        fragments.add(PopularFragment.newInstance(tabs[2]))

        val adapter = PagerAdapter(tabs, fragments, supportFragmentManager)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = 2

        view_tab.setupWithViewPager(view_pager)
        indicator.setViewPager(view_pager)

        navigation.setNavigationItemSelectedListener(this)
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun error(err: String) {
        toast(err)
    }

    override fun success(msg: String) {
        toast(msg)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.search_item -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer.closeDrawer(Gravity.LEFT)
        when (item.itemId) {
            R.id.home_item -> {

            }
            R.id.download_item -> {
                startActivity(Intent(this, DownloadActivity::class.java))
            }
            R.id.set_item -> {

            }
            R.id.about_item -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }
        return true
    }

    override fun onLatestFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onOldestFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPopularFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
