package com.marvin.splashedinkkotlin.ui.search

import androidx.lifecycle.LifecycleOwner
import com.marvin.splashedinkkotlin.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Administrator on 2017/8/3.
 */
class SearchPresenter : BasePresenter<SearchView>() {
    private val model = SearchModel()

    fun doSearch(query: String, page: Int, per_page: Int) {
        if (query == "") {
            mView?.error("请输入搜索关键词")
        } else {
            mView?.showProgress()
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val response = model.doSearch(query, page, per_page)
                    mView?.upData(response)
                    mView?.hideProgress()
                } catch (e: Throwable) {
                    mView?.hideProgress()
                    e.message?.let { mView?.error(it) }
                }
            }
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
    }
}