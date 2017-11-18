package com.marvin.splashedinkkotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by Marvin on 2017/7/29.
 * @param ctx 上下文
 * @param name 数据库名称
 * @param factory 生产游标工厂类，null使用默认
 * @param version 数据库版本
 */
class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "splashed_ink_kotlin", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    /**
     * 数据库创建
     */
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.createTable("download_lists",
                true,
                "photo_id" to TEXT + PRIMARY_KEY,
                "url" to TEXT,
                "preview_url" to TEXT,
                "path" to TEXT,
                "issuccess" to TEXT)
        p0?.createTable("history_search",
                true,
                "search_text" to TEXT + PRIMARY_KEY)
    }

    /**
     * 数据库更新
     */
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        
    }
}