package com.marvin.splashedinkkotlin.ui.search.adapter

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.support.annotation.LayoutRes
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.bean.SearchBean
import com.marvin.splashedinkkotlin.db.DatabaseUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.backgroundColor
import zlc.season.rxdownload2.RxDownload

/**
 * Created by Administrator on 2017/8/4.
 */
class SearchAdapter(private val context: Context, @LayoutRes layoutResId: Int, data: MutableList<SearchBean.ResultsBean>) : BaseQuickAdapter<SearchBean.ResultsBean, BaseViewHolder>(layoutResId, data) {
    private var progress: ProgressDialog? = null

    override fun convert(helper: BaseViewHolder, item: SearchBean.ResultsBean) {
        helper.setText(R.id.name, item.user!!.name)
        val background = helper.getView<FrameLayout>(R.id.background)
        val image = helper.getView<ImageView>(R.id.item_image)
        background.backgroundColor = Color.parseColor(item.color)
        Glide.with(context)
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
                        .subscribe {
                            download_bean ->
                            run {
                                progress?.dismiss()
                                RxDownload.getInstance(context)
                                        .serviceDownload(download_bean.url, item?.id + ".jpg")
                                        .subscribe {
                                            Toast.makeText(context, "任务已加入下载队列", Toast.LENGTH_SHORT)
                                        }
                                DatabaseUtils.insert_download_lists(context, item?.id!!, download_bean.url!!, item?.urls?.regular!!)
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