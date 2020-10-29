package com.marvin.splashedinkkotlin.network

import com.marvin.splashedinkkotlin.bean.*
import com.marvin.splashedinkkotlin.common.APIConfig
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Administrator on 2017/7/28.
 */
interface NetWorkService {
    companion object {
        lateinit var retrofitService: NetWorkService
    }

    @GET("photos?client_id=${APIConfig.Application_ID}")
    suspend fun getPhotoList(@Query("page") page: Int,
                     @Query("per_page") per_page: Int,
                     @Query("order_by") order_by: String): MutableList<NewPhotoBeanItem>

    @GET("photos/{photoId}?client_id=${APIConfig.Application_ID}")
    suspend fun getPhotoStatus(@Path("photoId") photoId: String): PhotoStatusBean

    @GET("photos/{photoId}/download?client_id=${APIConfig.Application_ID}")
    suspend fun getDownLoadUrl(@Path("photoId") photoId: String): DownLoadBean

    @GET("search/photos?client_id=${APIConfig.Application_ID}")
    suspend fun searchPhoto(@Query("query") query: String,
                    @Query("page") page: Int,
                    @Query("per_page") per_page: Int): SearchBean
}