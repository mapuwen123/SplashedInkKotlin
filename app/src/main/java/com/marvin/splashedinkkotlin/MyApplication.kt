package com.marvin.splashedinkkotlin

import android.app.Application
import android.content.Context
import com.marvin.splashedinkkotlin.base.BaseRetrofit
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.network.NetWorkService
import com.marvin.splashedinkkotlin.utils.SDCardUtil
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import retrofit2.Retrofit
import zlc.season.rxdownload2.RxDownload
import java.io.File

/**
 * Created by Administrator on 2017/7/26.
 */
class MyApplication : Application() {
    companion object {
        lateinit var retrofit: Retrofit
        lateinit var retrofitService: NetWorkService
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

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
                .defaultSavePath(BuildConfig.download_file)
                .maxThread(3)
                .maxRetryCount(3)
                .maxDownloadNumber(5)

        if (SDCardUtil.isSDCardEnable()) {
            if (!File(BuildConfig.image_cache).exists()) {
                File(BuildConfig.image_cache).mkdirs()
            }
        }

        Logger.d(BuildConfig.image_cache)
        Logger.d(BuildConfig.download_file)
    }


}