package com.marvin.splashedinkkotlin.ui.main

import com.marvin.splashedinkkotlin.bean.NewPhotoBeanItem
import com.marvin.splashedinkkotlin.network.NetWorkService

/**
 * Created by Administrator on 2017/7/28.
 */
class MainModel {
    suspend fun getPhotos(page: Int, per_page: Int): MutableList<NewPhotoBeanItem>
            = NetWorkService.retrofitService.getPhotoList(page, per_page, "latest")
}