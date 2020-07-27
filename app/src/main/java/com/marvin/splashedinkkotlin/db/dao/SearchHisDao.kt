package com.marvin.splashedinkkotlin.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marvin.splashedinkkotlin.db.entity.SearchHisEntity

/**
 *
 * @ClassName:      SearchHisDao
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/26 17:52
 */
@Dao
interface SearchHisDao {
    @Insert
    fun insert(vararg searchHisEntity: SearchHisEntity)

    @Query("select search_text from search_his")
    fun queryAll(): List<String>
}