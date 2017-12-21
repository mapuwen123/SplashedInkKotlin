package com.marvin.splashedinkkotlin.ui.set

import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.utils.DataCleanManager
import java.io.File

/**
 * Created by Administrator on 2017/8/19.
 */
class SetModel {
    fun getQualityText(): String {
        val qualitys = MyApplication.context.resources.getStringArray(R.array.quality_array)
        return qualitys[BuildConfig.image_quality]
    }

    fun getCacheSize(cacheFile: File): String {
        return DataCleanManager.getCacheSize(cacheFile)
    }
}