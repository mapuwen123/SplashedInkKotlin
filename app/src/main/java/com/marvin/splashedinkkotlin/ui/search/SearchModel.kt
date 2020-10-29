package com.marvin.splashedinkkotlin.ui.search

import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.bean.SearchBean
import com.marvin.splashedinkkotlin.network.NetWorkService
import io.reactivex.Observable

/**
 * Created by Administrator on 2017/8/3.
 */
class SearchModel {
    suspend fun doSearch(query: String, page: Int, per_page: Int): SearchBean =
            NetWorkService.retrofitService.searchPhoto(query, page, per_page)
}