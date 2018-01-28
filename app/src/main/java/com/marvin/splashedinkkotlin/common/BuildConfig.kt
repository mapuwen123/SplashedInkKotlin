package com.marvin.splashedinkkotlin.common

import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.utils.SDCardUtil

/**
 * Created by Administrator on 2017/7/28.
 */

object BuildConfig {
    val buglyKey = "e09a5178b8"

    val download_file = MyApplication.context.getExternalFilesDir("Download").absolutePath!!
    //    val database_file = MyApplication.context.getExternalFilesDir("Database").absolutePath
    val image_cache = SDCardUtil.diskCachePath + "/image"

    var image_quality = 2

    val isDebug = true

    val imgQuality = mapOf("RAW" to 0, "FULL" to 1, "REGULAR" to 2, "SMALL" to 3, "THUMB" to 4)
}
