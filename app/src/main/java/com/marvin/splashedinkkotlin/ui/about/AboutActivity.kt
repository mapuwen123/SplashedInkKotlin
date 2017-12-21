package com.marvin.splashedinkkotlin.ui.about

import android.content.Intent
import android.net.Uri
import android.view.View
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseActivity
import com.marvin.splashedinkkotlin.utils.PackageUtils
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity<AboutView, AboutPresenter>(), AboutView,
        View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun initPresenter(): AboutPresenter {
        return AboutPresenter()
    }

    override fun actionbarInit() {
        setSupportActionBar(toolbar)
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        supportActionBar?.title = getString(R.string.action_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun dataInit() {
        text_version.text = PackageUtils.getVersionName(this)

        linear_github.setOnClickListener(this)
        linear_mail.setOnClickListener(this)
        linear_code.setOnClickListener(this)

        linear_anko.setOnClickListener(this)
        linear_anko_sqlite.setOnClickListener(this)
        linear_glide.setOnClickListener(this)
        linear_base_recycler_view_adapter.setOnClickListener(this)
        linear_retrofit.setOnClickListener(this)
        linear_rxjava.setOnClickListener(this)
        linear_rxdownload.setOnClickListener(this)
        linear_ink_page_indicator.setOnClickListener(this)
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

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.linear_github -> openBrowser(getString(R.string.web_my_github))
            R.id.linear_mail -> sendMail(getString(R.string.my_email))
            R.id.linear_code -> openBrowser(getString(R.string.web_project_url))
            R.id.linear_anko -> openBrowser(getString(R.string.web_anko))
            R.id.linear_anko_sqlite -> openBrowser(getString(R.string.web_anko_sqlite))
            R.id.linear_glide -> openBrowser(getString(R.string.web_glide))
            R.id.linear_base_recycler_view_adapter -> openBrowser(getString(R.string.web_base_recycler_view_adapter_helper))
            R.id.linear_retrofit -> openBrowser(getString(R.string.web_retrofit))
            R.id.linear_rxjava -> openBrowser(getString(R.string.web_rxjava))
            R.id.linear_rxdownload -> openBrowser(getString(R.string.web_rxdownload))
            R.id.linear_ink_page_indicator -> openBrowser(getString(R.string.web_ink_page_indicator))
        }
    }

    private fun openBrowser(url: String) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val uri = Uri.parse(url)
        intent.data = uri
        startActivity(intent)
    }

    private fun sendMail(mail_addr: String) {
        val uri = arrayOf(mail_addr)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, uri)
        intent.putExtra(Intent.EXTRA_SUBJECT, "问题反馈")
        intent.putExtra(Intent.EXTRA_TEXT, "内容")
        intent.type = "message/rfc822"
        startActivity(Intent.createChooser(intent, "mail"))
    }
}
