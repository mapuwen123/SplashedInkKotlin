package com.marvin.splashedinkkotlin.ui.search

import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.bean.SearchBean
import io.reactivex.Observable

/**
 * Created by Administrator on 2017/8/3.
 */
class SearchModel {
    fun doSearch(query: String, page: Int, per_page: Int): Observable<SearchBean> {
        val observable = MyApplication.retrofitService.searchPhoto(query, page, per_page)
        return observable
    }
}