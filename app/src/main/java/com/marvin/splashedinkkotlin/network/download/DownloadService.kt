package com.marvin.splashedinkkotlin.network.download

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by Administrator on 2017/7/28.
 */
interface DownloadService {
    companion object {
        lateinit var retrofitService: DownloadService
    }

    @Streaming
    @GET
    suspend fun download(@Header("RANGE") start: String, @Url url: String): ResponseBody
}