package com.marvin.splashedinkkotlin.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.marvin.splashedinkkotlin.utils.ActivityUtils

/**
 * Created by Administrator on 2017/7/26.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<V, P : BasePresenter<V>> : AppCompatActivity() {
    companion object {
        val TAG: String = this::class.java.simpleName
    }

    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtils.addActivity(this)
        setContentView(getLayoutId())
        presenter = initPresenter()
        presenter.attach(this as V)
        actionbarInit()
        dataInit()
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initPresenter(): P

    protected abstract fun actionbarInit()

    protected abstract fun dataInit()

    override fun onDestroy() {
        ActivityUtils.removeActivity(this)
        super.onDestroy()
    }

    fun exit() {
        ActivityUtils.appExit(this)
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}