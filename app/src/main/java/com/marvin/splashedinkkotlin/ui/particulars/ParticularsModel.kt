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
        return MyApplication.retrofitService.getPhotoStatus(photoId)
    }

    fun getDownloadUrl(photoId: String): Observable<DownLoadBean> {
        return MyApplication.retrofitService.getDownLoadUrl(photoId)
    }
}