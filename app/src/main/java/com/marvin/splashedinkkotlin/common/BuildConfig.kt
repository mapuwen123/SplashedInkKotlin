package com.marvin.splashedinkkotlin.common

import com.marvin.splashedinkkotlin.MyApplication

/**
 * Created by Administrator on 2017/7/28.
 */

object BuildConfig {
    val download_file = MyApplication.context.getExternalFilesDir("Download").absolutePath
//    val database_file = MyApplication.context.getExternalFilesDir("Database").absolutePath
    val image_cache = MyApplication.context.externalCacheDir.absolutePath + "/image"
    val isDebug = true
}
