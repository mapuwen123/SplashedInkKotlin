package com.marvin.splashedinkkotlin.ui.search.adapter

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.support.annotation.LayoutRes
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.bean.SearchBean
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.db.DatabaseUtils
import com.marvin.splashedinkkotlin.utils.glide.GlideApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.backgroundColor
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.Mission
import zlc.season.rxdownload3.core.Succeed

/**
 * Created by Administrator on 2017/8/4.
 */
class SearchAdapter(private val context: Context, @LayoutRes layoutResId: Int, data: MutableList<SearchBean.ResultsBean>) : BaseQuickAdapter<SearchBean.ResultsBean, BaseViewHolder>(layoutResId, data) {
    private var progress: ProgressDialog? = null
    private var disposable: Disposable? = null

    override fun convert(helper: BaseViewHolder, item: SearchBean.ResultsBean) {
        helper.setText(R.id.name, item.user!!.name)
        val background = helper.getView<FrameLayout>(R.id.background)
        val image = helper.getView<ImageView>(R.id.item_image)
        background.backgroundColor = Color.parseColor(item.color)
        GlideApp.with(context)
                .load(item.urls!!.regular)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image)
        val download = helper?.getView<ImageView>(R.id.download)
        download.setOnClickListener {
            run {
                showDialog()
                MyApplication.retrofitService.getDownLoadUrl(item?.id!!)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { download_bean ->
                            run {
                                progress?.dismiss()
                                Toast.makeText(context, "任务已加入下载队列", Toast.LENGTH_SHORT).show()
                                val mission = Mission(download_bean.url!!, item?.id + ".jpg", BuildConfig.download_file)
                                disposable = RxDownload.create(mission)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe { status ->
                                            when (status) {
                                                is Succeed -> {
                                                    DatabaseUtils.update_download_lists(context, item?.id!!, "0")
                                                    disposable?.dispose()
                                                }
                                            }
                                        }
                                DatabaseUtils.insert_download_lists(context, item?.id!!, download_bean.url!!, item?.urls?.regular!!, "1")
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