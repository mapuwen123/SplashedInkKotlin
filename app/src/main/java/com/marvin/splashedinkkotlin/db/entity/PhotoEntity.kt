package com.marvin.splashedinkkotlin.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class PhotoEntity(
        @PrimaryKey val photoId: String,
        @ColumnInfo var savePath: String,
        @ColumnInfo var contentLength: Long,
        @ColumnInfo var readLength: Long,
        @ColumnInfo var url: String,
        @ColumnInfo var status: Int
)