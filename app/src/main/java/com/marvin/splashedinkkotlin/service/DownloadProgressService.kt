package com.marvin.splashedinkkotlin.service

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import androidx.core.app.JobIntentService
import com.marvin.splashedinkkotlin.db.AppDataBase
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity
import com.orhanobut.logger.Logger

/**
 *
 * @ClassName:      DownloadProgressService
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/30 16:21
 */
class DownloadProgressService : JobIntentService(), Handler.Callback {
    private lateinit var handler: Handler

    private lateinit var dm: DownloadManager
    private var downloadId = 0L

    private lateinit var url: String
    private lateinit var photoId: String
    private lateinit var imageUrl: String

    companion object {
        fun enqueueWork(context: Context, jobId: Int, intent: Intent) {
            enqueueWork(context, DownloadProgressService::class.java, jobId, intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        handler = Handler(mainLooper, this)
    }

    override fun onHandleWork(intent: Intent) {
        url = intent.getStringExtra("URL")!!
        photoId = intent.getStringExtra("PHOTO_ID")!!
        imageUrl = intent.getStringExtra("IMAGE_URL")!!
        downloadId = intent.getLongExtra("DOWNLOAD_ID", 0L)
        updateView()
    }

    private fun updateView() {
        val bytesAndStatus = getBytesAndStatus(downloadId)
        val currentSize = bytesAndStatus[0]
        val totalSize = bytesAndStatus[1]
        val status = bytesAndStatus[2]
        handler.obtainMessage(0, currentSize, totalSize, status).sendToTarget()
    }

    private fun getBytesAndStatus(downloadId: Long): List<Int> {
        val bytesAndStatus = mutableListOf<Int>(-1, -1, 0)
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = dm.query(query)
        cursor?.also {
            cursor.moveToFirst()
            bytesAndStatus[0] = it.getInt(it.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            bytesAndStatus[1] = it.getInt(it.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            bytesAndStatus[2] = it.getInt(it.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
        }?.close()
        return bytesAndStatus
    }

    override fun handleMessage(message: Message): Boolean {
        when (message.obj as Int) {
            2 -> {
                updateView()
            }
            8 -> {
                AppDataBase.db.diskDownloadDao()
                        .update(DiskDownloadEntity(
                                photoId,
                                url,
                                imageUrl,
                                "0",
                                downloadId,
                                0
                        ))
            }
            16 -> {
                AppDataBase.db.diskDownloadDao()
                        .update(DiskDownloadEntity(
                                photoId,
                                url,
                                imageUrl,
                                "1",
                                downloadId,
                                0
                        ))
            }
            else -> {
                updateView()
            }
        }
//        if (message.arg1 == message.arg2) {
//
//        } else {
//            updateView()
//        }
        Logger.i("-*-*-*-currentSize:${message.arg1} totalSize:${message.arg2} status:${message.obj}")
        return true
    }
}