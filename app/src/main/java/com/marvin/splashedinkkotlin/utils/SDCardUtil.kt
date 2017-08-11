package com.marvin.splashedinkkotlin.utils

import android.content.Context
import android.os.Environment
import android.os.StatFs

import com.orhanobut.logger.Logger

import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException

/**
 * SD卡相关的辅助类
 * Created by mapuw on 15/12/27.
 */
class SDCardUtil private constructor() {
    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {

        /**
         * 判断SDCard是否可用

         * @return
         */
        val isSDCardEnable: Boolean
            get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        /**
         * 获取SD卡路径

         * @return
         */
        val sdCardPath: String
            get() = Environment.getExternalStorageDirectory().absolutePath + File.separator

        /**
         * 获取SD卡的剩余容量 单位byte

         * @return
         */
        // 获取空闲的数据块的数量
        // 获取单个数据块的大小（byte）
        val sdCardAllSize: Long
            get() {
                if (isSDCardEnable) {
                    val stat = StatFs(sdCardPath)
                    val availableBlocks = stat.availableBlocks.toLong() - 4
                    val freeBlocks = stat.availableBlocks.toLong()
                    return freeBlocks * availableBlocks
                }
                return 0
            }

        /**
         * 获取指定路径所在空间的剩余可用容量字节数，单位byte

         * @param filePath
         * *
         * @return 容量字节 SDCard可用空间，内部存储可用空间
         */
        fun getFreeBytes(filePath: String): Long {
            var filePath = filePath
            // 如果是sd卡的下的路径，则获取sd卡可用容量
            if (filePath.startsWith(sdCardPath)) {
                filePath = sdCardPath
            } else {// 如果是内部存储的路径，则获取内存存储的可用容量
                filePath = Environment.getDataDirectory().absolutePath
            }
            val stat = StatFs(filePath)
            val availableBlocks = stat.availableBlocks.toLong() - 4
            return stat.blockSize * availableBlocks
        }

        /**
         * 获取系统存储路径

         * @return
         */
        fun getRootDirectoryPath(context: Context): String {
            return Environment.getRootDirectory().absolutePath
        }

        fun put(context: Context, fileDir: String, fileName: String, content: String) {
            val filedir = File(fileDir)
            val jsonfile = File(filedir, fileName)
            if (!filedir.exists()) {
                filedir.mkdirs()
            }
            var outputstream: FileOutputStream? = null
            try {
                jsonfile.createNewFile()
                if (isSDCardEnable) {
                    outputstream = FileOutputStream(jsonfile)
                    val buffer = content.toByteArray()
                    outputstream.write(buffer)
                    outputstream.flush()
                    //Toast.makeText(context, "文件写入成功", Toast.LENGTH_SHORT).show();
                }
            } catch (ex: Exception) {
                //Toast.makeText(context, "文件写入失败", Toast.LENGTH_SHORT).show();
                Logger.d(ex.toString())
            } finally {
                if (outputstream != null) {
                    try {
                        outputstream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }

        operator fun get(context: Context, fileDir: String, fileName: String): String? {
            var sb: StringBuffer? = null
            try {
                val file = File(fileDir, fileName)
                val br = BufferedReader(FileReader(file))
                var readline = ""
                sb = StringBuffer()
                while (br.readLine() != null) {
                    readline = br.readLine()
                    println("readline:" + readline)
                    sb.append(readline)
                }
//                while ((readline = br.readLine()) != null) {
//                    println("readline:" + readline)
//                    sb.append(readline)
//                }
                br.close()
                return sb.toString()
                //Toast.makeText(context, fileName + "文件读取成功", Toast.LENGTH_SHORT).show();
            } catch (e: Exception) {
                e.printStackTrace()
                //Toast.makeText(context, fileName + "文件读取失败", Toast.LENGTH_SHORT).show();
            }

            return null
        }
    }


}
