package com.marvin.splashedinkkotlin.db

import android.content.Context
import com.marvin.splashedinkkotlin.bean.DiskDownloadBean
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update

/**
 * Created by Marvin on 2017/7/29.
 * 数据库操作类
 */
class DatabaseUtils {
    companion object {
        /**
         * 下载列表-插入
         */
        fun insert_download_lists(context: Context, photo_id: String, url: String, preview_url: String, is_success: String): Long {
            var result = 0L

            MyDatabaseOpenHelper.getInstance(context).use {
                result = insert("download_lists",
                        "photo_id" to photo_id,
                        "url" to url,
                        "preview_url" to preview_url,
                        "issuccess" to is_success)
            }
            return result
        }

        /**
         * 下载列表-查询
         */
        fun select_download_lists(context: Context): MutableList<DiskDownloadBean>? {
            var datas: MutableList<DiskDownloadBean>? = ArrayList()

            MyDatabaseOpenHelper.getInstance(context).use {
                select("download_lists", "photo_id", "url", "preview_url", "issuccess").exec {
                    if (count > 0) {
                        while (moveToNext()) {
                            val disk = DiskDownloadBean()
                            disk.photo_id = getString(0)
                            disk.url = getString(1)
                            disk.preview_url = getString(2)
                            disk.isSuccess = getString(3)
                            datas?.add(disk)
                        }
                    }
                }
            }
            return datas
        }

        /**
         * 下载列表-删除
         */
        fun delete_download_lists(context: Context, photo_id: String): Int {
            var result = 0

            MyDatabaseOpenHelper.getInstance(context).use {
                result = delete("download_lists", "photo_id = {id}", "id" to photo_id)
            }
            return result
        }

        /**
         * 下载列表-更新
         */
        fun update_download_lists(context: Context, photo_id: String, is_success: String): Int {
            var result = 0

            MyDatabaseOpenHelper.getInstance(context).use {
                result = update("download_lists", "issuccess" to is_success)
                        .whereArgs("photo_id = {id}", "id" to photo_id)
                        .exec()
            }
            return result
        }

        /**
         * 用户搜索记录-插入
         */
        fun insert_history_search(context: Context, search_text: String): Long {
            var result = 0L
            MyDatabaseOpenHelper.getInstance(context).use {
                result = insert("history_search",
                        "search_text" to search_text)
            }
            return result
        }

        /**
         * 用户搜索记录-查询
         */
        fun select_history_search(context: Context): MutableList<String>? {
            val datas: MutableList<String>? = ArrayList()
            MyDatabaseOpenHelper.getInstance(context).use {
                val cursor = query("history_search", null, null, null, null, null, null)
                if (cursor.count > 0) {
                    while (cursor.moveToNext()) {
                        datas?.add(cursor.getString(0))
                    }
                    cursor.close()
                }
            }
            return datas
        }

    }
}