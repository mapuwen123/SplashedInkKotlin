package com.marvin.splashedinkkotlin.ui.download

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.bean.DiskDownloadBean
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.db.DatabaseUtils
import org.jetbrains.anko.backgroundResource
import zlc.season.rxdownload2.RxDownload
import zlc.season.rxdownload2.entity.DownloadFlag
import java.io.File

/**
 * Created by Administrator on 2017/7/31.
 */
class DownloadAdapter(private val activity: DownloadActivity, @LayoutRes layoutResId: Int, data: MutableList<DiskDownloadBean>?) : BaseQuickAdapter<DiskDownloadBean, BaseViewHolder>(layoutResId, data) {
    var is_start = true

    override fun convert(helper: BaseViewHolder?, item: DiskDownloadBean?) {
        helper?.setText(R.id.text_photo_id, item?.photo_id)
        Glide.with(activity)
                .load(item?.preview_url)
                .transition(withCrossFade())
                .into(helper?.getView(R.id.background))
        val iv_down_status = helper?.getView<ImageView>(R.id.iv_down_status)
        val iv_down_reset_look = helper?.getView<ImageView>(R.id.iv_down_reset_look)

        val disposable = RxDownload.getInstance(activity).receiveDownloadStatus(item?.url)
                .subscribe { event ->
                    run {
                        if (event.flag == DownloadFlag.FAILED) {
                            val throwable = event.error
                            error(throwable)
                        }
                        if (event.flag == DownloadFlag.COMPLETED) {
                            helper?.setText(R.id.text_photo_id, item?.photo_id)
                            iv_down_status?.backgroundResource = R.drawable.download_complete
                            iv_down_reset_look?.backgroundResource = R.drawable.download_look
                            iv_down_reset_look?.tag = true
                            MediaStore.Images.Media.insertImage(activity.contentResolver, BuildConfig.download_file, item?.photo_id + ".jpg", null)
                            activity.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(BuildConfig.download_file
                                    + "/" + item?.photo_id + ".jpg"))))
                        } else if (event.flag == DownloadFlag.PAUSED) {
                            helper?.setText(R.id.text_photo_id, item?.photo_id + ":" + event.downloadStatus.percent)
                            iv_down_status?.backgroundResource = R.drawable.download_midway
                            iv_down_reset_look?.backgroundResource = R.drawable.download_start
                            iv_down_reset_look?.tag = false
                            is_start = false
                        } else if (event.flag == DownloadFlag.STARTED) {
                            helper?.setText(R.id.text_photo_id, item?.photo_id + ":" + event.downloadStatus.percent)
                            iv_down_status?.backgroundResource = R.drawable.download_midway
                            iv_down_reset_look?.backgroundResource = R.drawable.download_pause
                            iv_down_reset_look?.tag = false
                            is_start = true
                        }
                    }
                }
        iv_down_reset_look?.setOnClickListener(adapterItemClick(activity, item?.url!!, item?.photo_id!!))
        helper?.getView<ImageView>(R.id.iv_down_close)?.setOnClickListener(adapterItemClick(activity, item?.url!!, item?.photo_id!!))
    }

    inner class adapterItemClick(private val activity: DownloadActivity,
                                 private val url: String,
                                 private val id: String) : View.OnClickListener {
        override fun onClick(p0: View?) {
            when (p0?.id) {
                R.id.iv_down_reset_look -> {
                    if (p0.tag as Boolean) {

                    } else {
                        if (is_start) {
                            RxDownload.getInstance(activity).pauseServiceDownload(url).subscribe()
                            is_start = false
                        } else {
                            RxDownload.getInstance(activity)
                                    .serviceDownload(url, id + ".jpg").subscribe()
                            is_start = true
                        }
                    }
                }
                R.id.iv_down_close -> {
                    RxDownload.getInstance(activity).deleteServiceDownload(url, true).subscribe()
                    DatabaseUtils.delete_download_lists(activity, id)
                    activity.presenter.getDownloadList(activity)
                }
            }
        }

    }
}