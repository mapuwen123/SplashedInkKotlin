package com.marvin.splashedinkkotlin

import android.app.Application
import com.marvin.splashedinkkotlin.base.BaseRetrofit
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.network.NetWorkService
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import retrofit2.Retrofit
import zlc.season.rxdownload2.RxDownload

/**
 * Created by Administrator on 2017/7/26.
 */
class MyApplication : Application() {
    companion object {
        lateinit var retrofit: Retrofit
        lateinit var retrofitService: NetWorkService
    }

    override fun onCreate() {
        super.onCreate()

        // retrofit初始化
        retrofit = BaseRetrofit.getRetrofit(this)!!
        retrofitService = retrofit.create(NetWorkService::class.java)

        // logger初始化
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.isDebug
            }
        })

        // rxdownload初始化
        RxDownload.getInstance(this)
                .retrofit(retrofit)
                .defaultSavePath(BuildConfig.AppDir + "/Download")
                .maxThread(3)
                .maxRetryCount(3)
                .maxDownloadNumber(5)
    }
}