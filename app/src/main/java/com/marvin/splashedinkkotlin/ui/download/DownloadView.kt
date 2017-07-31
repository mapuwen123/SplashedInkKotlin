package com.marvin.splashedinkkotlin.ui.download

import com.marvin.splashedinkkotlin.base.BaseView
import com.marvin.splashedinkkotlin.bean.DiskDownloadBean

/**
 * Created by Administrator on 2017/7/31.
 */
interface DownloadView : BaseView {
    fun onUpdata(data: MutableList<DiskDownloadBean>)
}