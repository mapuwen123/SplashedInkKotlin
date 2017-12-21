package com.marvin.splashedinkkotlin.ui.set

import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.base.BasePresenter
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.utils.DataCleanManager
import com.marvin.splashedinkkotlin.utils.SPUtils
import java.io.File

/**
 * Created by Administrator on 2017/8/19.
 */
class SetPresenter : BasePresenter<SetView>() {
    private val model = SetModel()

    fun setQuality(quality: Int) {
        BuildConfig.image_quality = quality
        SPUtils.put(MyApplication.context, "QUALITY", quality)
    }

    fun getQualityText() {
        mView?.setQualityText(model.getQualityText())
    }

    fun getCacheSize(cacheFile: File) {
        mView?.setCacheSize(model.getCacheSize(cacheFile))
    }

    fun clearCache(cachePath: String) {
        DataCleanManager.deleteFolderFile(cachePath, true)
        getCacheSize(File(cachePath))
        mView?.success("缓存清除成功")
    }
}