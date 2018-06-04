package com.marvin.splashedinkkotlin.base

import java.lang.ref.WeakReference

/**
 * Created by Administrator on 2017/7/26.
 */
abstract class BasePresenter<V> {
    var mView: V? = null

    fun attach(mView: V) {
        val weakView = WeakReference<V>(mView)
        this.mView = weakView.get()
    }
}
