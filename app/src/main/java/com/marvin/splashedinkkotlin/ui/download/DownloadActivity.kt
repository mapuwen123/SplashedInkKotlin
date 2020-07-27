package com.marvin.splashedinkkotlin.ui.download

import android.os.Handler
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseActivity
import com.marvin.splashedinkkotlin.bean.DiskDownloadBean
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity
import kotlinx.android.synthetic.main.activity_down_load.*

class DownloadActivity : BaseActivity<DownloadView, DownloadPresenter>(), DownloadView {
    private val data = mutableListOf<DiskDownloadEntity>()
    private val adapter = DownloadAdapter(this, R.layout.download_item, data)

    override fun getLayoutId(): Int {
        return R.layout.activity_down_load
    }

    override fun initPresenter(): DownloadPresenter {
        return DownloadPresenter()
    }

    override fun actionbarInit() {
        setSupportActionBar(toolbar)
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        supportActionBar?.title = getString(R.string.action_download)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun dataInit() {
        adapter.emptyView = layoutInflater.inflate(R.layout.recycler_empty_iew, null)

        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler.adapter = adapter

        presenter.getDownloadList(this)
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

    override fun onUpdata(data: List<DiskDownloadEntity>) {
        this.data.clear()
        this.data.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
