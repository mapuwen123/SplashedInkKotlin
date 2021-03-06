package com.marvin.splashedinkkotlin.ui.download

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.marvin.splashedinkkotlin.base.BasePresenter

/**
 * Created by Administrator on 2017/7/31.
 */
class DownloadPresenter : BasePresenter<DownloadView>() {
    private val model = DownloadModel()

    fun getDownloadList(context: Context) {
        val data = model.getDownloadList(context)
        data?.let { mView?.onUpdata(it) }
    }

    override fun onCreate(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
    }
}