package com.marvin.splashedinkkotlin.ui.particulars

import android.content.Context
import android.content.Intent
import android.widget.TextView
import com.marvin.splashedinkkotlin.base.BasePresenter
import com.marvin.splashedinkkotlin.bean.DownLoadBean
import com.marvin.splashedinkkotlin.bean.PhotoStatusBean
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Administrator on 2017/7/28.
 */
class ParticularsPresenter : BasePresenter<ParticularsView>(), Observer<PhotoStatusBean> {
    private val model = ParticularsModel()

    private val typeNameMap = mapOf(
            0 to "尺寸",
            1 to "曝光时间",
            2 to "颜色",
            3 to "光圈",
            4 to "地点",
            5 to "焦距",
            6 to "设备",
            7 to "曝光",
            8 to "喜欢",
            9 to "查看",
            10 to "下载"
    )

    fun getPhotoStatus(photoId: String) {
        model.getPhotoStatus(photoId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this)
    }

    fun getDownloadUrl(photoId: String) {
        mView?.showProgress()
        model.getDownloadUrl(photoId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(DownLoadUrlObservable())
    }

    fun doShare(context: Context, url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, url)
        intent.type = "text/plain"
        context.startActivity(Intent.createChooser(intent, "分享到"))
    }

    fun onTextClick(type: Int, text: TextView) {
        val typeName = typeNameMap[type].toString()
        mView?.showSnackbar("$typeName: ${text.text}")
    }

    private var disposable: Disposable? = null

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onComplete() {
        disposable?.dispose()
    }

    override fun onNext(t: PhotoStatusBean) {
        t.user?.profile_image?.medium?.let { mView?.setAuthorHeader(it) }
        t.user?.name?.let { mView?.setAuthorName(it) }

        t.created_at?.substring(0, 9)?.let { mView?.setCreateTime(it) }

        mView?.setSize("${t.width}*${t.height}")

        t.exif?.exposure_time?.let { mView?.setShutterTime(it) }
        t.exif?.aperture?.let { mView?.setAperture(it) }
        t.exif?.focal_length?.let { mView?.setFocal(it) }
        t.exif?.model?.let { mView?.setCameraName(it) }
        t.exif?.iso?.let { mView?.setExposure(it.toString()) }

        t.color?.let { mView?.setColor(it) }

        t.location?.title?.let { mView?.setAddr(it) }

        t.likes.let { mView?.setLikes(it.toString()) }
        t.views.let { mView?.setViews(it.toString()) }
        t.downloads.let { mView?.setDownloads(it.toString()) }
    }

    override fun onError(e: Throwable) {
        mView?.error(e.message.toString())
        disposable?.dispose()
    }

    inner class DownLoadUrlObservable : Observer<DownLoadBean> {

        override fun onNext(t: DownLoadBean) {
            t.url?.let { mView?.setDownloadUrl(it) }
        }

        override fun onError(e: Throwable) {
            mView?.hideProgress()
            mView?.error(e.message.toString())
            disposable?.dispose()
        }

        override fun onComplete() {
            mView?.hideProgress()
            disposable?.dispose()
        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

    }
}