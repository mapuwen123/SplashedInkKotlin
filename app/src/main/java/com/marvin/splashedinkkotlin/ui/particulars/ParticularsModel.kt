package com.marvin.splashedinkkotlin.ui.particulars

import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.bean.DownLoadBean
import com.marvin.splashedinkkotlin.bean.PhotoStatusBean
import io.reactivex.Observable

/**
 * Created by Administrator on 2017/7/28.
 */
class ParticularsModel {
    fun getPhotoStatus(photoId: String): Observable<PhotoStatusBean> {
        val observable = MyApplication.retrofitService.getPhotoStatus(photoId)
        return observable
    }

    fun getDownloadUrl(photoId: String): Observable<DownLoadBean> {
        val observable = MyApplication.retrofitService.getDownLoadUrl(photoId)
        return observable
    }
}