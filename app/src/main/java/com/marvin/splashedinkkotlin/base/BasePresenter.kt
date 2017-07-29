package com.marvin.splashedinkkotlin.base

/**
 * Created by Administrator on 2017/7/26.
 */
abstract class BasePresenter<V> {
    var mView: V? = null

    fun attach(mView: V) {
        this.mView = mView
    }

    fun dettach() {
        this.mView = null
    }
}