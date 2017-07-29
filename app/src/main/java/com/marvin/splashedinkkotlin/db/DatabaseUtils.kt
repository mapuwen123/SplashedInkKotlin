package com.marvin.splashedinkkotlin.db

import android.content.Context
import com.marvin.splashedinkkotlin.bean.DownloadListsBean
import org.jetbrains.anko.db.insert

/**
 * Created by Administrator on 2017/7/29.
 */
class DatabaseUtils {
    companion object {
        fun insert_download_lists(context: Context, download_lists: DownloadListsBean): Long {
            var result = 0L

            MyDatabaseOpenHelper.getInstance(context).use {
                result = insert("download_lists",
                        "photo_id" to download_lists.photo_id,
                        "url" to download_lists.url,
                        "preview_url" to download_lists.preview_url)
            }
            return result
        }

        fun select_download_lists(context: Context, offset: Int, count: Int) {
            MyDatabaseOpenHelper.getInstance(context).use {
//                select("download_lists").parseList()
            }
        }
    }
}