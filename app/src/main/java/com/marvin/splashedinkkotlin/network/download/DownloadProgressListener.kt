package com.marvin.splashedinkkotlin.network.download

interface DownloadProgressListener {
    fun onStart()
    fun onProgress(read: Long, contentLength: Long)
    fun onSuccess()
    fun onError(e: Throwable)
}