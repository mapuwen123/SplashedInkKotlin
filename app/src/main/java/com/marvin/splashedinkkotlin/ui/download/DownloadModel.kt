package com.marvin.splashedinkkotlin.ui.download

import android.content.Context
import com.marvin.splashedinkkotlin.bean.DiskDownloadBean
import com.marvin.splashedinkkotlin.db.DatabaseUtils

/**
 * Created by Administrator on 2017/7/31.
 */
class DownloadModel {
    fun getDownloadList(context: Context): MutableList<DiskDownloadBean>? {
        val data = DatabaseUtils.select_download_lists(context)
        return data
    }
}