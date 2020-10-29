package com.marvin.splashedinkkotlin.service

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.app.JobIntentService
import com.marvin.splashedinkkotlin.db.AppDataBase
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity
import zlc.season.ironbranch.mainThread

/**
 *
 * @ClassName:      DownloadService
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/27 11:12
 */
class DownloadService : JobIntentService() {

    private lateinit var dm: DownloadManager
    private var downloadId = 0L

    private lateinit var url: String
    private lateinit var photoId: String
    private lateinit var imageUrl: String

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, DownloadService::class.java, 1, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        url = intent.getStringExtra("URL")!!
        photoId = intent.getStringExtra("PHOTO_ID")!!
        imageUrl = intent.getStringExtra("IMAGE_URL")!!
        val photo = AppDataBase.db.diskDownloadDao().queryById(photoId)
        dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        if (photo == null) {
            val resource = Uri.parse(url)
            val request = DownloadManager.Request(resource)
            request.setDestinationInExternalFilesDir(this, "/download", "$photoId.jpg")
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setTitle("$photoId.jpg")
            downloadId = dm.enqueue(request)
            AppDataBase.db.diskDownloadDao()
                    .insert(DiskDownloadEntity(
                            photoId,
                            url,
                            imageUrl,
                            "1",
                            downloadId,
                            0L
                    ))
        } else {
            if (photo.isError != 0L) {
                mainThread {
                    Toast.makeText(this, "该任务已在下载计划中", Toast.LENGTH_SHORT).show()
                }
            } else {
                val resource = Uri.parse(url)
                val request = DownloadManager.Request(resource)
                request.setDestinationInExternalFilesDir(this, "/download", "$photoId.jpg")
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setTitle("$photoId.jpg")
                downloadId = dm.enqueue(request)
                AppDataBase.db.diskDownloadDao()
                        .update(DiskDownloadEntity(
                                photoId,
                                url,
                                imageUrl,
                                "1",
                                downloadId,
                                0L
                        ))
            }
        }
    }

//    override fun onCreate() {
//        super.onCreate()
//        contentResolver.registerContentObserver(contentUri, true, observer)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        contentResolver.unregisterContentObserver(observer)
//    }


}