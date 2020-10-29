package com.marvin.splashedinkkotlin.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @ClassName:      DiskDownload
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/26 17:30
 */
@Entity(tableName = "disk_download")
data class DiskDownloadEntity(
        @PrimaryKey val photoId: String,
        @ColumnInfo val url: String,
        @ColumnInfo(name = "preview_url") val previewUrl: String,
        @ColumnInfo(name = "is_success") var isSuccess: String,
        @ColumnInfo(name = "download_id") val downloadId: Long,
        @ColumnInfo(name = "is_error") val isError: Long
)