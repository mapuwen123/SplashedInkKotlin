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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.backgroundResource
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.*
import java.io.File

/**
 * Created by Administrator on 2017/7/31.
 */
class DownloadAdapter(private val activity: DownloadActivity, @LayoutRes layoutResId: Int, data: MutableList<DiskDownloadBean>?) : BaseQuickAdapter<DiskDownloadBean, BaseViewHolder>(layoutResId, data) {

    private var disposable: Disposable? = null

    override fun convert(helper: BaseViewHolder?, item: DiskDownloadBean?) {
        helper?.setText(R.id.text_photo_id, item?.photo_id)
        Glide.with(activity)
                .load(item?.preview_url)
                .transition(withCrossFade())
                .into(helper?.getView(R.id.background))
        val iv_down_status = helper?.getView<ImageView>(R.id.iv_down_status)
        val iv_down_reset_look = helper?.getView<ImageView>(R.id.iv_down_reset_look)

        val mission = Mission(item?.url!!, item.photo_id + ".jpg", BuildConfig.download_file)
        if (item.isSuccess == "0") {
            helper?.setText(R.id.text_photo_id, item.photo_id)
            iv_down_status?.backgroundResource = R.drawable.download_complete
            iv_down_reset_look?.backgroundResource = R.drawable.download_look
            iv_down_reset_look?.tag = "true-false"
        } else {
            disposable = RxDownload.create(mission)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { status ->
                        when (status) {
                            is Failed -> {
                                helper?.setText(R.id.text_photo_id, item.photo_id + ":下载失败")
                                iv_down_status?.backgroundResource = R.drawable.download_midway
                                iv_down_reset_look?.backgroundResource = R.drawable.download_start
                                iv_down_reset_look?.tag = "true-false"
                            }
                            is Waiting -> {
                                helper?.setText(R.id.text_photo_id, item.photo_id + ":等待中")
                                iv_down_status?.backgroundResource = R.drawable.download_midway
                                iv_down_reset_look?.backgroundResource = R.drawable.download_start
                                iv_down_reset_look?.tag = "true-false"
                            }
                            is Succeed -> {
                                helper?.setText(R.id.text_photo_id, item.photo_id)
                                iv_down_status?.backgroundResource = R.drawable.download_complete
                                iv_down_reset_look?.backgroundResource = R.drawable.download_look
                                iv_down_reset_look?.tag = "true-false"
                                MediaStore.Images.Media.insertImage(activity.contentResolver, BuildConfig.download_file, item.photo_id + ".jpg", null)
                                activity.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(BuildConfig.download_file
                                        + "/" + item.photo_id + ".jpg"))))

                                DatabaseUtils.update_download_lists(activity, item.photo_id!!, "0")

                                //下载成功后取消订阅
                                disposable?.dispose()
                            }
                            is Suspend -> {
                                helper?.setText(R.id.text_photo_id, item.photo_id + ":" + status.percent())
                                iv_down_status?.backgroundResource = R.drawable.download_midway
                                iv_down_reset_look?.backgroundResource = R.drawable.download_start
                                iv_down_reset_look?.tag = "false-false"
                            }
                            is Downloading -> {
                                helper?.setText(R.id.text_photo_id, item.photo_id + ":" + status.percent())
                                iv_down_status?.backgroundResource = R.drawable.download_midway
                                iv_down_reset_look?.backgroundResource = R.drawable.download_pause
                                iv_down_reset_look?.tag = "false-true"
                            }
                        }
                    }
        }
        iv_down_reset_look?.setOnClickListener(adapterItemClick(activity, mission, item.photo_id!!))
        helper?.getView<ImageView>(R.id.iv_down_close)?.setOnClickListener(adapterItemClick(activity, mission, item.photo_id!!))
    }

    inner class adapterItemClick(private val activity: DownloadActivity,
                                 private val mission: Mission,
                                 private val id: String) : View.OnClickListener {
        override fun onClick(p0: View?) {
            when (p0?.id) {
                R.id.iv_down_reset_look -> {
                    if (p0.tag.toString().split("-")[0] == "true") {

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
                    DatabaseUtils.delete_download_lists(activity, id)
                    activity.presenter.getDownloadList(activity)
                }
            }
        }

    }
}