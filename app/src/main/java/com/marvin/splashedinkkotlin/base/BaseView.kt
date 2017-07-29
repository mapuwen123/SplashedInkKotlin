package com.marvin.splashedinkkotlin.base

/**
 * Created by Administrator on 2017/7/26.
 */
abstract interface BaseView {
    fun showProgress()
    fun hideProgress()
    fun error(err: String)
    fun success(msg: String)
}