package com.marvin.splashedinkkotlin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Typeface
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.db.AppDataBase
import com.marvin.splashedinkkotlin.db.entity.SearchHisEntity
import com.marvin.splashedinkkotlin.utils.SDCardUtil
import com.marvin.splashedinkkotlin.utils.SPUtils
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import org.jetbrains.anko.doAsync
import java.io.File
import kotlin.concurrent.thread

/**
 * Created by Marvin on 2017/7/26.
 */
class MyApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        BuildConfig.image_quality = (SPUtils[context, "QUALITY", 2] as Int?)!!

        val typeFace = Typeface.createFromAsset(assets, "fonts/Courier.ttf")
        val field = Typeface::class.java.getDeclaredField("SERIF")
        field.isAccessible = true
        field.set(null, typeFace)

        Bugly.init(this, BuildConfig.buglyKey, BuildConfig.isDebug)
        Beta.autoCheckUpgrade = true

        if (SDCardUtil.isSDCardEnable) {
            if (!File(BuildConfig.image_cache).exists()) {
                File(BuildConfig.image_cache).mkdirs()
            }
        }

//        thread {
//            AppDataBase.db.searchHisDao().insert(SearchHisEntity(1, "aaa"))
//        }.start()
    }


}