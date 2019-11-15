package com.marvin.splashedinkkotlin.ui.main

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.marvin.splashedinkkotlin.base.BasePresenter
import com.marvin.splashedinkkotlin.bean.PhotoBean
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Administrator on 2017/7/28.
 */
class MainPresenter : BasePresenter<MainView>(), Observer<MutableList<PhotoBean>> {
    private val model = MainModel()

    fun getPhotos(page: Int, per_page: Int) {
        if (page == 1) {
            mView?.showProgress()
        }
        model.getPhotos(page, per_page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this)
    }

    private var disposable: Disposable? = null

    override fun onError(e: Throwable) {
        mView?.hideProgress()
        mView?.error(e.message.toString())
        disposable?.dispose()
    }

    override fun onNext(t: MutableList<PhotoBean>) {
//        mView?.upData(t)
    }

    override fun onComplete() {
        mView?.hideProgress()
        disposable?.dispose()
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onCreate(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
        disposable?.dispose()
    }
}