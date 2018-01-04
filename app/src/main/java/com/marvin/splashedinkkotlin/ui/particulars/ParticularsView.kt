package com.marvin.splashedinkkotlin.ui.particulars

import com.marvin.splashedinkkotlin.base.BaseView

/**
 * Created by Administrator on 2017/7/28.
 */
interface ParticularsView : BaseView {
    fun shareSuccess(msg: String)
    fun setAuthorHeader(url: String)
    fun setAuthorName(name: String)
    fun setCreateTime(time: String)
    fun setSize(size: String)
    fun setShutterTime(time: String)
    fun setColor(color: String)
    fun setAperture(aperture: String)
    fun setAddr(addr: String)
    fun setFocal(focal: String)
    fun setCameraName(name: String)
    fun setExposure(exposure: String)
    fun setLikes(likes: String)
    fun setViews(views: String)
    fun setDownloads(downloads: String)
    fun setDownloadUrl(url: String)
    fun showSnackbar(message: String)
}