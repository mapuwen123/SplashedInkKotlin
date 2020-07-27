package com.marvin.splashedinkkotlin.ui.download

import com.marvin.splashedinkkotlin.base.BaseView
import com.marvin.splashedinkkotlin.bean.DiskDownloadBean
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity

/**
 * Created by Administrator on 2017/7/31.
 */
interface DownloadView : BaseView {
    fun onUpdata(data: List<DiskDownloadEntity>)
}