package com.marvin.splashedinkkotlin.network.download

import android.os.Environment
import com.marvin.splashedinkkotlin.MyApplication
import com.orhanobut.logger.Logger
import okhttp3.ResponseBody
import retrofit2.http.Body
import java.io.*

class FileSaveUtils(private val listener: DownloadProgressListener) {
    private fun getPhotoPath(): File {
        val photoPath = MyApplication.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath
        val file = File(photoPath!!)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }

    fun savePhoto(photoId: String, body: ResponseBody) {
        val file = File("${getPhotoPath().absolutePath}${File.separator}$photoId.jpeg")
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            val fileReader = ByteArray(4096)
            val fileSize = body.contentLength()
            var fileSizeDownloaded = 0L
            inputStream = body.byteStream()
            outputStream = FileOutputStream(file)
            while (true) {
                val read = inputStream.read(fileReader)
                if (read == -1) {
                    break
                }
                outputStream.write(fileReader, 0, read)
                fileSizeDownloaded += read
                listener.onProgress(fileSizeDownloaded, fileSize)
            }
            outputStream.flush()
        } catch (e: IOException) {
            listener.onError(e)
        } finally {
            inputStream?.close()
            outputStream?.close()
            listener.onSuccess()
        }
    }
}

