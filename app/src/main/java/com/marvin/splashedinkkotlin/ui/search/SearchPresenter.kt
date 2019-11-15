package com.marvin.splashedinkkotlin.ui.search

import androidx.lifecycle.LifecycleOwner
import com.marvin.splashedinkkotlin.base.BasePresenter
import com.marvin.splashedinkkotlin.bean.SearchBean
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Administrator on 2017/8/3.
 */
class SearchPresenter : BasePresenter<SearchView>(), Observer<SearchBean> {
    private val model = SearchModel()

    fun doSearch(query: String, page: Int, per_page: Int) {
        if (query == "") {
            mView?.error("请输入搜索关键词")
        } else {
            mView?.showProgress()
            model.doSearch(query, page, per_page)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this)
        }
    }

    private var disposable: Disposable? = null

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onComplete() {
        mView?.hideProgress()
        disposable?.dispose()
    }

    override fun onError(e: Throwable) {
        mView?.hideProgress()
        e.message?.let { mView?.error(it) }
        disposable?.dispose()
    }

    override fun onNext(t: SearchBean) {
        mView?.upData(t)
    }

    override fun onCreate(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
        disposable?.dispose()
    }
}