package com.marvin.splashedinkkotlin.ui.download

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.db.AppDataBase
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity
import com.marvin.splashedinkkotlin.widget.OnProgressListener
import com.marvin.splashedinkkotlin.widget.ProgressTextView
import io.reactivex.disposables.Disposable
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.Mission

/**
 * Created by Administrator on 2017/7/31.
 */
class DownloadAdapter(private val activity: DownloadActivity,
                      @LayoutRes layoutResId: Int,
                      data: MutableList<DiskDownloadEntity>?) :
        BaseQuickAdapter<DiskDownloadEntity, BaseViewHolder>(layoutResId, data) {

    private var disposable: Disposable? = null

    override fun convert(helper: BaseViewHolder?, item: DiskDownloadEntity?) {
        helper?.setText(R.id.text_photo_id, item?.photoId)
        Glide.with(activity)
                .load(item?.previewUrl)
                .transition(withCrossFade())
                .into(helper!!.getView(R.id.background))
        val iv_down_status = helper.getView<ImageView>(R.id.iv_down_status)
        val iv_down_reset_look = helper.getView<ImageView>(R.id.iv_down_reset_look)
        val text_photo_id = helper.getView<ProgressTextView>(R.id.text_photo_id)

        val mission = Mission(item?.url!!,
                item.photoId + ".jpg",
                BuildConfig.download_file)
        if (item.isSuccess == "0") {
            helper.setText(R.id.text_photo_id, item.photoId)
            iv_down_status?.setBackgroundResource(R.drawable.download_complete)
            iv_down_reset_look?.setBackgroundResource(R.drawable.download_look)
            iv_down_reset_look?.tag = "true-false"
        } else {
            text_photo_id.setDownloadId(item.downloadId).start()
            text_photo_id.setOnProgressListener(object : OnProgressListener {
                override fun onDownloading(progress: Int) {
                    iv_down_status?.setBackgroundResource(R.drawable.download_midway)
                    iv_down_reset_look?.setBackgroundResource(R.drawable.download_pause)
                    iv_down_reset_look?.tag = "false-false"
                }

                override fun onError() {
                    AppDataBase.db.diskDownloadDao()
                            .update(DiskDownloadEntity(
                                    item.photoId,
                                    item.url,
                                    item.previewUrl,
                                    "1",
                                    item.downloadId,
                                    item.isError
                            ))
                    helper.setText(R.id.text_photo_id, item.photoId + ":下载失败")
                    iv_down_status?.setBackgroundResource(R.drawable.download_midway)
                    iv_down_reset_look?.setBackgroundResource(R.drawable.download_start)
                    iv_down_reset_look?.tag = "false-false"
                }

                override fun onSuccess() {
                    AppDataBase.db.diskDownloadDao()
                            .update(DiskDownloadEntity(
                                    item.photoId,
                                    item.url,
                                    item.previewUrl,
                                    "0",
                                    item.downloadId,
                                    item.isError
                            ))
                    helper.setText(R.id.text_photo_id, item.photoId)
                    iv_down_status?.setBackgroundResource(R.drawable.download_complete)
                    iv_down_reset_look?.setBackgroundResource(R.drawable.download_look)
                    iv_down_reset_look?.tag = "true-false"
                }
            })
        }
        iv_down_reset_look?.setOnClickListener(adapterItemClick(activity, mission, item))
        helper.getView<ImageView>(R.id.iv_down_close)?.setOnClickListener(adapterItemClick(activity,
                mission,
                item))
    }

    inner class adapterItemClick(private val activity: DownloadActivity,
                                 private val mission: Mission,
                                 private val item: DiskDownloadEntity) : View.OnClickListener {
        override fun onClick(p0: View?) {
            when (p0?.id) {
                R.id.iv_down_reset_look -> {
                    if (p0.tag.toString().split("-")[0] == "true") {
                        Toast.makeText(activity, "点击查看", Toast.LENGTH_SHORT).show()
                    } else {
                        if (p0.tag.toString().split("-")[1] == "true") {
                            RxDownload.stop(mission).subscribe()
                            p0.tag = "false-false"
                        } else {
                            RxDownload.start(mission).subscribe()
                            p0.tag = "false-true"
                        }
                    }
                }
                R.id.iv_down_close -> {
                    RxDownload.delete(mission, true).subscribe()
                    RxDownload.clear(mission).subscribe()
                    AppDataBase.db.diskDownloadDao().delete(item)
//                    DatabaseUtils.delete_download_lists(activity, id)
                    activity.presenter.getDownloadList(activity)
                }
            }
        }

    }
}