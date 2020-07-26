package com.marvin.splashedinkkotlin.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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
@Database(entities = [DiskDownloadEntity::class, SearchHisEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        val db = Room.databaseBuilder(
                MyApplication.context,
                AppDataBase::class.java,
                "splashed_ink_data"
        ).build()
    }

    abstract fun diskDownloadDao(): DiskDownloadDao
    abstract fun searchHisDao(): SearchHisDao
}