package com.marvin.splashedinkkotlin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Typeface
import com.marvin.splashedinkkotlin.base.BaseRetrofit
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.network.NetWorkService
import com.marvin.splashedinkkotlin.utils.SDCardUtil
import com.marvin.splashedinkkotlin.utils.SPUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import retrofit2.Retrofit
import zlc.season.rxdownload3.core.DownloadConfig
import java.io.File

/**
 * Created by Marvin on 2017/7/26.
 */
class MyApplication : Application() {
    companion object {
        lateinit var retrofit: Retrofit
        lateinit var retrofitService: NetWorkService
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        BuildConfig.image_quality = (SPUtils[context, "QUALITY", 2] as Int?)!!

        val type_face = Typeface.createFromAsset(assets, "fonts/Courier.ttf")
        val field = Typeface::class.java.getDeclaredField("SERIF")
        field.isAccessible = true
        field.set(null, type_face)

        // Bugly初始化
        Bugly.init(context, BuildConfig.buglyKey, BuildConfig.isDebug)
        Beta.autoCheckUpgrade = true

        // retrofit初始化
        retrofit = BaseRetrofit.getRetrofit()!!
        retrofitService = retrofit.create(NetWorkService::class.java)

        // logger初始化
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.isDebug
            }
        })

        // rxdownload初始化
        val builder = DownloadConfig.Builder.create(this)
                .setFps(20)                         //设置更新频率
                .enableAutoStart(true)              //自动开始下载
                .enableService(true)                        //启用Service
                .enableNotification(true)                   //启用Notification

        DownloadConfig.init(builder)

        if (SDCardUtil.isSDCardEnable) {
            if (!File(BuildConfig.image_cache).exists()) {
                File(BuildConfig.image_cache).mkdirs()
            }
        }
    }


}