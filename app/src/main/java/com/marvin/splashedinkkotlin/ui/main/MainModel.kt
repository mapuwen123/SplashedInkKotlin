package com.marvin.splashedinkkotlin.ui.main

import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.bean.PhotoBean
import com.marvin.splashedinkkotlin.network.NetWorkService
import io.reactivex.Observable

/**
 * Created by Administrator on 2017/7/28.
 */
class MainModel {
    fun getPhotos(page: Int, per_page: Int): Observable<MutableList<PhotoBean>>
            = NetWorkService.retrofitService.getPhotoList(page, per_page, "latest")
}