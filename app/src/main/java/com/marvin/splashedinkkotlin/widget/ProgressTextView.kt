package com.marvin.splashedinkkotlin.widget

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import com.marvin.splashedinkkotlin.db.AppDataBase
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity
import com.marvin.splashedinkkotlin.utils.ThreadUtils
import com.marvin.splashedinkkotlin.utils.execute
import com.orhanobut.logger.Logger

/**
 *
 * @ClassName:      ProgressTextView
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/30 16:34
 */
class ProgressTextView : androidx.appcompat.widget.AppCompatTextView, Handler.Callback {
    private var mContext: Context
    private lateinit var mHandler: Handler
    private lateinit var dm: DownloadManager
    private var mDownloadId: Long = 0L

    private var mOnProgressListener: OnProgressListener? = null

    constructor(context: Context) : super(context) {
        mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        mContext = context
        init()
    }

    private fun init() {
        mHandler = Handler(mContext.mainLooper, this)
        dm = mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    private fun updateView() {
        kotlin.run {
            val bytesAndStatus = getBytesAndStatus(mDownloadId)
            val currentSize = bytesAndStatus[0]
            val totalSize = bytesAndStatus[1]
            val status = bytesAndStatus[2]
            mHandler.obtainMessage(0, currentSize, totalSize, status).sendToTarget()
        }
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

    @SuppressLint("SetTextI18n")
    override fun handleMessage(message: Message): Boolean {
        Logger.i("-*-*-*-currentSize:${message.arg1} totalSize:${message.arg2} status:${message.obj}")
        when (message.obj as Int) {
            2 -> {
                val far = message.arg1.toFloat()
                val size = message.arg2.toFloat()
                val progress: Int = (far / size * 100).toInt()
                text = "$progress%"
                updateView()
            }
            8 -> {
//                AppDataBase.db.diskDownloadDao()
//                        .update(DiskDownloadEntity(
//                                photoId,
//                                url,
//                                imageUrl,
//                                "0",
//                                downloadId
//                        ))
                text = "100%"
                mOnProgressListener?.onSuccess()
            }
            16 -> {
//                AppDataBase.db.diskDownloadDao()
//                        .update(DiskDownloadEntity(
//                                photoId,
//                                url,
//                                imageUrl,
//                                "1",
//                                downloadId
//                        ))
                mOnProgressListener?.onError()
            }
            else -> {
                updateView()
            }
        }
        return true
    }

    fun setDownloadId(downloadId: Long): ProgressTextView {
        mDownloadId = downloadId
        return this
    }

    fun start() {
        updateView()
    }

    fun setOnProgressListener(listener: OnProgressListener) {
        mOnProgressListener = listener
    }
}