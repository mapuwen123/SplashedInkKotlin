package com.marvin.splashedinkkotlin.ui.main

import androidx.lifecycle.LifecycleOwner
import com.marvin.splashedinkkotlin.base.BasePresenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Administrator on 2017/7/28.
 */
class MainPresenter : BasePresenter<MainView>() {
    private val model = MainModel()

    fun getPhotos(page: Int, per_page: Int) {
        if (page == 1) {
            mView?.showProgress()
        }
        GlobalScope.launch {
            try {
                val photos = model.getPhotos(page, per_page)
                if (photos.isNotEmpty()) {
                    mView?.hideProgress()
                }
            } catch (e: Throwable) {
                mView?.hideProgress()
                mView?.error(e.message.toString())
            }
        }
//        model.getPhotos(page, per_page)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
    }
}