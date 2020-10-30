package com.marvin.splashedinkkotlin.network.download

class DownloadQueues private constructor() : HashMap<String, DownloadManager>() {
    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DownloadQueues()
        }
    }

    fun enqueueTask(downloadManager: DownloadManager) {
        val photoId = downloadManager.photoEntity?.photoId
        photoId?.let { put(it, downloadManager) }
    }

    fun remove(downloadManager: DownloadManager) {
        val photoId = downloadManager.photoEntity?.photoId
        photoId?.let { remove(photoId) }
    }
}