package com.marvin.splashedinkkotlin.ui.main

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseActivity
import com.marvin.splashedinkkotlin.ui.about.AboutActivity
import com.marvin.splashedinkkotlin.ui.download.DownloadActivity
import com.marvin.splashedinkkotlin.ui.main.adapter.PagerAdapter
import com.marvin.splashedinkkotlin.ui.main.fragment.LatestFragment
import com.marvin.splashedinkkotlin.ui.main.fragment.OldestFragment
import com.marvin.splashedinkkotlin.ui.main.fragment.PopularFragment
import com.marvin.splashedinkkotlin.ui.search.SearchActivity
import com.marvin.splashedinkkotlin.ui.set.SetActivity
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_pager_main.*
import kotlinx.android.synthetic.main.drawer_main.*

class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView,
        NavigationView.OnNavigationItemSelectedListener,
        LatestFragment.OnFragmentInteractionListener,
        OldestFragment.OnFragmentInteractionListener,
        PopularFragment.OnFragmentInteractionListener,
        Toolbar.OnMenuItemClickListener {
    private var mDrawerToggle: ActionBarDrawerToggle? = null

    private val tabs: MutableList<String> = ArrayList()

    private val fragments: MutableList<androidx.fragment.app.Fragment> = ArrayList()

    private var fTime: Long = 0L
    private var sTime: Long = 0L

    override fun onPostCreate(savedInstanceState: Bundle?) {
        mDrawerToggle?.syncState()
        super.onPostCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        sTime = System.currentTimeMillis()
        val diff = sTime - fTime
        if (diff <= 2000) {
            exit()
        } else {
            fTime = System.currentTimeMillis()
            Toast.makeText(this, "再次返回退出应用", Toast.LENGTH_SHORT).show()
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return mDrawerToggle?.onOptionsItemSelected(item)!! || super.onOptionsItemSelected(item)
//    }

    override fun onConfigurationChanged(newConfig: Configuration) {
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
        val toolbar: Toolbar = findViewById(R.id.toolbar)
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
        view_pager.offscreenPageLimit = 1

        view_tab.setupWithViewPager(view_pager)
        indicator.setViewPager(view_pager)

        navigation.setNavigationItemSelectedListener(this)

        RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if (!it) {
                        Toast.makeText(this, "未获取到权限，将导致无法正常下载图片！", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun error(err: String) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
    }

    override fun success(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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
                startActivity(Intent(this, SetActivity::class.java))
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
