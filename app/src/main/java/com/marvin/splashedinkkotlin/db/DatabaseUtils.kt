package com.marvin.splashedinkkotlin.db

import android.content.Context
import com.marvin.splashedinkkotlin.bean.DiskDownloadBean
import com.marvin.splashedinkkotlin.bean.PhotoBean
import org.jetbrains.anko.db.insert

/**
 * Created by Administrator on 2017/7/29.
 */
class DatabaseUtils {
    companion object {
        fun insert_download_lists(context: Context, photo_id: String, url: String, preview_url: String): Long {
            var result = 0L

            MyDatabaseOpenHelper.getInstance(context).use {
                result = insert("download_lists",
                        "photo_id" to photo_id,
                        "url" to url,
                        "preview_url" to preview_url)
            }
            return result
        }

        fun select_download_lists(context: Context): MutableList<DiskDownloadBean>? {
//            var result: List<Triple<String, String, String>>? = null
            var datas: MutableList<DiskDownloadBean>? = ArrayList()

            MyDatabaseOpenHelper.getInstance(context).use {
                val cursor = query("download_lists", null, null, null, null, null, null)
                if (cursor.count > 0) {
                    while (cursor.moveToNext()) {
                        val disk = DiskDownloadBean()
                        disk.photo_id = cursor.getString(0)
                        disk.url = cursor.getString(1)
                        disk.preview_url = cursor.getString(2)
                        datas?.add(disk)
                    }
                    cursor.close()
                }
            }
            return datas
        }

        fun delete_download_lists(context: Context, photo_id: String): Int {
            var result = 0

            val whereArgs = arrayOf(photo_id)

            MyDatabaseOpenHelper.getInstance(context).use {
                result = delete("download_lists",
                        "photo_id=?",
                        whereArgs)
            }
            return result
        }

    }
}