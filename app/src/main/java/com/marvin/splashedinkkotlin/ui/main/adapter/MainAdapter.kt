package com.marvin.splashedinkkotlin.ui.main.adapter

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import androidx.annotation.LayoutRes
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.bean.NewPhotoBeanItem
import com.marvin.splashedinkkotlin.bean.PhotoBean
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.db.AppDataBase
import com.marvin.splashedinkkotlin.db.entity.DiskDownloadEntity
import com.marvin.splashedinkkotlin.network.NetWorkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.backgroundColor
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.Mission
import zlc.season.rxdownload3.core.Succeed

/**
 * Created by Administrator on 2017/7/11.
 */

class MainAdapter(private val context: Context, @LayoutRes layoutResId: Int, data: MutableList<NewPhotoBeanItem>?) : BaseQuickAdapter<NewPhotoBeanItem, BaseViewHolder>(layoutResId, data) {
    private var progress: ProgressDialog? = null
    private var disposable: Disposable? = null

    override fun convert(helper: BaseViewHolder, item: NewPhotoBeanItem) {
        helper.setText(R.id.name, item.user!!.name)
        val background = helper.getView<FrameLayout>(R.id.background)
        val image = helper.getView<ImageView>(R.id.item_image)
        background.backgroundColor = Color.parseColor(item.color)
        var imageUrl: String = when (BuildConfig.image_quality) {
            BuildConfig.imgQuality["RAW"] -> item.urls?.raw.toString()
            BuildConfig.imgQuality["FULL"] -> item.urls?.full.toString()
            BuildConfig.imgQuality["REGULAR"] -> item.urls?.regular.toString()
            BuildConfig.imgQuality["SMALL"] -> item.urls?.small.toString()
            BuildConfig.imgQuality["THUMB"] -> item.urls?.thumb.toString()
            else -> ({
            }).toString()
        }
        Glide.with(context)
                .load(imageUrl)
                .transition(withCrossFade())
                .into(image)
        val download = helper.getView<ImageView>(R.id.download)
        download.setOnClickListener {
            run {
                showDialog()
                NetWorkService.retrofitService.getDownLoadUrl(item.id!!)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { download_bean ->
                            run {
                                progress?.dismiss()
                                Toast.makeText(context, "任务已加入下载队列", Toast.LENGTH_SHORT).show()
                                val mission = Mission(download_bean.url!!, item.id + ".jpg", BuildConfig.download_file)
                                disposable = RxDownload.create(mission)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe { status ->
                                            when (status) {
                                                is Succeed -> {
                                                    val downloadEntity = AppDataBase.db.diskDownloadDao().queryById(item.id!!)
                                                    downloadEntity.isSuccess = "0"
                                                    AppDataBase.db.diskDownloadDao().update(downloadEntity)
//                                                    DatabaseUtils.update_download_lists(context, item.id!!, "0")
                                                    disposable?.dispose()
                                                }
                                            }
                                        }
//                                AppDataBase.db.diskDownloadDao()
//                                        .insert(DiskDownloadEntity(
//                                                item.id!!,
//                                                download_bean.url!!,
//                                                item.urls?.regular!!,
//                                                "1"
//                                        ))
//                                DatabaseUtils.insert_download_lists(context, item.id!!, download_bean.url!!, item.urls?.regular!!, "1")
                            }
                        }
            }
        }
    }

    private fun showDialog() {
        progress = ProgressDialog(context)
        progress!!.setMessage("下载准备中,请稍后...")
        progress!!.show()
    }
}

