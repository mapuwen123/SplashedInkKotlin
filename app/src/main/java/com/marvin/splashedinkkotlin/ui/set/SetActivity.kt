package com.marvin.splashedinkkotlin.ui.set

import android.view.View
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseActivity
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.utils.PackageUtils
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_set.*
import org.jetbrains.anko.*
import java.io.File

class SetActivity : BaseActivity<SetView, SetPresenter>(), SetView, View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.activity_set
    }

    override fun initPresenter(): SetPresenter {
        return SetPresenter()
    }

    override fun actionbarInit() {
        setSupportActionBar(toolbar)
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        supportActionBar?.title = getString(R.string.action_set)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun dataInit() {
        presenter.getQualityText()
        presenter.getCacheSize(File(BuildConfig.image_cache))

        set_quality.setOnClickListener(this)
        set_clear.setOnClickListener(this)
        set_update.setOnClickListener(this)

        set_tv_update.text = PackageUtils.getVersionName(this)
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error(err: String) {
        toast(err)
    }

    override fun success(msg: String) {
        toast(msg)
    }

    override fun setQualityText(text: String) {
        set_tv_quality.text = text
    }

    override fun setCacheSize(size: String) {
        set_tv_cache.text = size
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.set_quality -> {
                val selectorItems = listOf("超清", "高清", "普通", "快速", "极速")
                selector("请选择图片质量", selectorItems, { _, i ->
                    when (i) {
                        0 -> BuildConfig.imgQuality["RAW"]?.let { presenter.setQuality(it) }
                        1 -> BuildConfig.imgQuality["FULL"]?.let { presenter.setQuality(it) }
                        2 -> BuildConfig.imgQuality["REGULAR"]?.let { presenter.setQuality(it) }
                        3 -> BuildConfig.imgQuality["SMALL"]?.let { presenter.setQuality(it) }
                        4 -> BuildConfig.imgQuality["THUMB"]?.let { presenter.setQuality(it) }
                    }
                    presenter.getQualityText()
                })
            }
            R.id.set_clear -> {
                alert("是否要清空缓存？", "提示") {
                    yesButton { presenter.clearCache(BuildConfig.image_cache) }
                    noButton { dialog -> dialog.dismiss() }
                }.show()
            }
            R.id.set_update -> {
                Beta.checkUpgrade()
            }
        }
    }
}
