package com.marvin.splashedinkkotlin.ui.download

import android.content.Context
import com.marvin.splashedinkkotlin.db.AppDataBase
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by Administrator on 2017/7/31.
 */
class DownloadModel {
    fun getDownloadList(context: Context): List<DiskDownloadEntity>? =//        val data = DatabaseUtils.select_download_lists(context)
            runBlocking(Dispatchers.Default) {
                AppDataBase.db.diskDownloadDao().queryAll()
            }
}