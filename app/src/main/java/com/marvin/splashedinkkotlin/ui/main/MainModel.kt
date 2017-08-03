package com.marvin.splashedinkkotlin.ui.main

import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.bean.PhotoBean
import io.reactivex.Observable

/**
 * Created by Administrator on 2017/7/28.
 */
class MainModel {
    fun getPhotos(page: Int, per_page: Int): Observable<MutableList<PhotoBean>> {
        val observable = MyApplication.retrofitService.getPhotoList(page, per_page, "latest")
        return observable
    }
}