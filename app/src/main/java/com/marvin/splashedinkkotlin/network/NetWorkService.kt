package com.marvin.splashedinkkotlin.network

import com.marvin.splashedinkkotlin.bean.DownLoadBean
import com.marvin.splashedinkkotlin.bean.PhotoBean
import com.marvin.splashedinkkotlin.bean.PhotoStatusBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Administrator on 2017/7/28.
 */
interface NetWorkService {
    @GET("photos?client_id=f7f954a8249404b33049059a8cce4b5147303f10b2089965dc0232c29d02f653")
    fun getPhotoList(@Query("page") page: Int,
                     @Query("per_page") per_page: Int) : Observable<MutableList<PhotoBean>>

    @GET("photos/{photoId}?client_id=f7f954a8249404b33049059a8cce4b5147303f10b2089965dc0232c29d02f653")
    fun getPhotoStatus(@Path("photoId") photoId: String) : Observable<PhotoStatusBean>

    @GET("photos/{photoId}/download?client_id=f7f954a8249404b33049059a8cce4b5147303f10b2089965dc0232c29d02f653")
    fun getDownLoadUrl(@Path("photoId") photoId: String) : Observable<DownLoadBean>
}