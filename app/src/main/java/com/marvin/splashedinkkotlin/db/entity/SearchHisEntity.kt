package com.marvin.splashedinkkotlin.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @ClassName:      SearchHis
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/26 17:37
 */
@Entity(tableName = "search_his")
data class SearchHisEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "search_text") val searchText: String
)