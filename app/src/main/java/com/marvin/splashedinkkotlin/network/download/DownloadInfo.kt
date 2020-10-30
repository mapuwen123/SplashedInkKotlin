package com.marvin.splashedinkkotlin.network.download

import com.marvin.splashedinkkotlin.network.NetWorkService

data class DownloadInfo(
        var photoId: String,
        var savePath: String,
        var contentLength: Long,
        var readLength: Long,
        var url: String
)