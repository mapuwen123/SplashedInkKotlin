package com.marvin.splashedinkkotlin.db.dao

import androidx.room.*
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity

/**
 *
 * @ClassName:      DiskDownloadDao
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/26 17:42
 */
@Dao
interface DiskDownloadDao {
    @Insert
    fun insert(vararg diskDownloadEntity: DiskDownloadEntity)

    @Query("select * from disk_download")
    fun queryAll(): List<DiskDownloadEntity>

    @Query("select * from disk_download where photoId = :photoId")
    fun queryById(photoId: String): DiskDownloadEntity?

    @Delete
    fun delete(diskDownloadEntity: DiskDownloadEntity)

    @Update
    fun update(diskDownloadEntity: DiskDownloadEntity)
}