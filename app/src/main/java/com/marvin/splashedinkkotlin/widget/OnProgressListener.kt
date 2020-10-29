package com.marvin.splashedinkkotlin.widget

/**
 *
 * @ClassName:      OnProgressListener
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/30 17:04
 */
interface OnProgressListener {
    fun onDownloading(progress: Int)
    fun onError()
    fun onSuccess()
}