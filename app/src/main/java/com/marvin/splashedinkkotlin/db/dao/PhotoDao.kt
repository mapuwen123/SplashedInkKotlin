package com.marvin.splashedinkkotlin.db.dao

import androidx.room.*
import com.marvin.splashedinkkotlin.db.entity.PhotoEntity

@Dao
interface PhotoDao {
    @Insert
    fun insert(vararg photoEntity: PhotoEntity)

    @Query("select * from photo where photoId = :photoId")
    fun selectByPhotoId(photoId: String): PhotoEntity?

    @Delete
    fun delete(photoEntity: PhotoEntity)

    @Update
    fun update(photoEntity: PhotoEntity)
}