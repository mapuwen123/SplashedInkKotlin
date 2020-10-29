package com.marvin.splashedinkkotlin.ui.particulars

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.marvin.splashedinkkotlin.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Administrator on 2017/7/28.
 */
class ParticularsPresenter : BasePresenter<ParticularsView>() {
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
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = model.getPhotoStatus(photoId)
                response.user?.profile_image?.medium?.let { mView?.setAuthorHeader(it) }
                response.user?.name?.let { mView?.setAuthorName(it) }

                response.created_at?.substring(0, 9)?.let { mView?.setCreateTime(it) }

                mView?.setSize("${response.width}*${response.height}")

                response.exif?.exposure_time?.let { mView?.setShutterTime(it) }
                response.exif?.aperture?.let { mView?.setAperture(it) }
                response.exif?.focal_length?.let { mView?.setFocal(it) }
                response.exif?.model?.let { mView?.setCameraName(it) }
                response.exif?.iso?.let { mView?.setExposure(it.toString()) }

                response.color?.let { mView?.setColor(it) }

                response.location?.title?.let { mView?.setAddr(it) }

                response.likes.let { mView?.setLikes(it.toString()) }
                response.views.let { mView?.setViews(it.toString()) }
                response.downloads.let { mView?.setDownloads(it.toString()) }
            } catch (e: Throwable) {
                mView?.hideProgress()
                mView?.error(e.message.toString())
            }
        }
//        model.getPhotoStatus(photoId)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this)
    }

    fun getDownloadUrl(photoId: String) {
        mView?.showProgress()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = model.getDownloadUrl(photoId)
                mView?.hideProgress()
                response.url?.let { mView?.setDownloadUrl(it) }
            } catch (e: Throwable) {
                mView?.hideProgress()
                mView?.error(e.message.toString())
            }
        }
//        model.getDownloadUrl(photoId)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this)
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

    override fun onCreate(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
    }
}