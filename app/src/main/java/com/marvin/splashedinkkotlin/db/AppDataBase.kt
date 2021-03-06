package com.marvin.splashedinkkotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.marvin.splashedinkkotlin.db.dao.DiskDownloadDao
import com.marvin.splashedinkkotlin.db.dao.PhotoDao
import com.marvin.splashedinkkotlin.db.dao.SearchHisDao
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity
import com.marvin.splashedinkkotlin.db.entity.PhotoEntity
import com.marvin.splashedinkkotlin.db.entity.SearchHisEntity

/**
 *
 * @ClassName:      AppDataBase
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/26 17:56
 */
@Database(entities = [DiskDownloadEntity::class, SearchHisEntity::class, PhotoEntity::class], version = 4)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        lateinit var db: AppDataBase

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE disk_download ADD COLUMN download_id INTEGER NOT NULL DEFAULT 10")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE disk_download ADD COLUMN is_error INTEGER NOT NULL DEFAULT 0")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS 'photo' (`photoId` TEXT PRIMARY KEY NOT NULL, `savePath` TEXT NOT NULL, `contentLength` INTEGER NOT NULL DEFAULT 0, `readLength` INTEGER NOT NULL DEFAULT 0, `url` TEXT NOT NULL, `status` INTEGER NOT NULL DEFAULT 0)")
            }
        }
    }

    abstract fun diskDownloadDao(): DiskDownloadDao
    abstract fun searchHisDao(): SearchHisDao
    abstract fun photoDao(): PhotoDao
}