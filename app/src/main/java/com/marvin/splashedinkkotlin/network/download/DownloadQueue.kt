package com.marvin.splashedinkkotlin.network.download

class DownloadQueues private constructor() : HashMap<String, DownloadManager>() {
    private val cacheList = mutableListOf<DownloadManager>()

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DownloadQueues()
        }
    }

    fun enqueueTask(downloadManager: DownloadManager) {
        if (size < 2) {
            val photoId = downloadManager.photoEntity?.photoId
            photoId?.let { put(it, downloadManager) }
            downloadManager.download()
        } else {
            cacheList.add(downloadManager)
        }
    }

    fun remove(downloadManager: DownloadManager) {
        val photoId = downloadManager.photoEntity?.photoId
        photoId?.let { remove(photoId) }
        if (size < 2 && cacheList.size > 0) {
            enqueueTask(cacheList[0])
            cacheList.removeAt(0)
        }
    }
}