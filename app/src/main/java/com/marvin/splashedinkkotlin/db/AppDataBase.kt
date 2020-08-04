package com.marvin.splashedinkkotlin.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.db.dao.DiskDownloadDao
import com.marvin.splashedinkkotlin.db.dao.SearchHisDao
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity
import com.marvin.splashedinkkotlin.db.entity.SearchHisEntity

/**
 *
 * @ClassName:      AppDataBase
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/26 17:56
 */
@Database(entities = [DiskDownloadEntity::class, SearchHisEntity::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        lateinit var db: AppDataBase

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE disk_download ADD COLUMN download_id INTEGER NOT NULL DEFAULT 10")
            }
        }
    }

    abstract fun diskDownloadDao(): DiskDownloadDao
    abstract fun searchHisDao(): SearchHisDao
}