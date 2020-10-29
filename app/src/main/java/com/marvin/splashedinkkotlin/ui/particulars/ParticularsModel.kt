package com.marvin.splashedinkkotlin.ui.particulars

import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.bean.DownLoadBean
import com.marvin.splashedinkkotlin.bean.PhotoStatusBean
import com.marvin.splashedinkkotlin.network.NetWorkService
import io.reactivex.Observable

/**
 * Created by Administrator on 2017/7/28.
 */
class ParticularsModel {
    suspend fun getPhotoStatus(photoId: String): PhotoStatusBean
            = NetWorkService.retrofitService.getPhotoStatus(photoId)

    suspend fun getDownloadUrl(photoId: String): DownLoadBean
            = NetWorkService.retrofitService.getDownLoadUrl(photoId)
}