package com.marvin.splashedinkkotlin.ui.particulars

import android.app.ProgressDialog
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.Color
import android.transition.Explode
import android.view.View
import android.view.Window
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseActivity
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.db.DatabaseUtils
import com.marvin.splashedinkkotlin.widget.ParallaxScrollView
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_particulars.*
import kotlinx.android.synthetic.main.profile_details.*
import kotlinx.android.synthetic.main.profile_header.*
import kotlinx.android.synthetic.main.profile_statistics.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import org.jetbrains.anko.wallpaperManager
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.Mission
import zlc.season.rxdownload3.core.Succeed

class ParticularsActivity : BaseActivity<ParticularsView, ParticularsPresenter>(), ParticularsView,
        View.OnClickListener,
        ParallaxScrollView.ScrollviewListener {

    var photo_id: String = ""
    var height: Int = 0
    var image_url = ""

    var manager: WallpaperManager? = null

    var progress_dialog: ProgressDialog? = null

    private var disposable: Disposable? = null

    override fun getLayoutId(): Int {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        window.exitTransition = Explode()
        return R.layout.activity_particulars
    }

    override fun initPresenter(): ParticularsPresenter {
        return ParticularsPresenter()
    }

    override fun actionbarInit() {
        setSupportActionBar(toolbar)
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        supportActionBar?.title = getString(R.string.action_particulars)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun dataInit() {
        photo_id = intent.getStringExtra("PHOTO_ID")
        height = intent.getIntExtra("HEIGHT", 0)
        image_url = intent.getStringExtra("IMAGE_URL")
        Logger.i(image_url)
        manager = wallpaperManager

        image.layoutParams.height = height
        Glide.with(this)
                .load(image_url)
                .transition(withCrossFade())
                .into(image)

        scroll.setScrollViewListener(this)
        btn_download.setOnClickListener(this)
        btn_share.setOnClickListener(this)
        btn_window.setOnClickListener(this)

        presenter.getPhotoStatus(photo_id)
    }

    override fun showProgress() {
        showProgressDialog("下载准备中,请稍后...")
    }

    override fun hideProgress() {
        hideProgressDialog()
    }

    override fun error(err: String) {
        toast(err)
    }

    override fun success(msg: String) {
        toast(msg)
    }

    override fun shareSuccess(msg: String) {
        toast(msg)
    }

    override fun setAuthorHeader(url: String) {
        val options = RequestOptions()
        options.circleCrop()
        Glide.with(this)
                .load(url)
                .apply(options)
                .transition(withCrossFade())
                .into(header_image)
    }

    override fun setAuthorName(name: String) {
        from.text = "来自于 " + name
    }

    override fun setCreateTime(time: String) {
        create_time.text = "创作于 " + time
    }

    override fun setSize(size: String) {
        text_size.text = size
    }

    override fun setShutterTime(time: String) {
        text_time.text = time
    }

    override fun setColor(color: String) {
        text_color.text = color
        view_color.backgroundColor = Color.parseColor(color)
    }

    override fun setAperture(aperture: String) {
        text_aperture.text = aperture
    }

    override fun setAddr(addr: String) {
        text_addr.text = addr
    }

    override fun setFocal(focal: String) {
        text_focal.text = focal
    }

    override fun setCameraName(name: String) {
        text_camera.text = name
    }

    override fun setExposure(exposure: String) {
        text_exposure.text = exposure
    }

    override fun setLikes(likes: String) {
        text_likes.text = likes
    }

    override fun setViews(views: String) {
        text_views.text = views
    }

    override fun setDownloads(downloads: String) {
        text_downloads.text = downloads
    }

    override fun setDownloadUrl(url: String) {
        val mission = Mission(url, photo_id + ".jpg", BuildConfig.download_file)
        toast("任务已加入下载队列")
        disposable = RxDownload.create(mission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { status ->
                    when (status) {
                        is Succeed -> {
                            DatabaseUtils.update_download_lists(this, photo_id, "0")
                            disposable?.dispose()
                        }
                    }
                }
        DatabaseUtils.insert_download_lists(this, photo_id, url, image_url, "1")
    }

    fun showProgressDialog(message: CharSequence) {
        progress_dialog = indeterminateProgressDialog(message.toString(), "", null)
    }

    fun hideProgressDialog() {
        progress_dialog?.let { it.dismiss() }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_download -> {
                presenter.getDownloadUrl(photo_id)
            }
            R.id.btn_share -> {
                presenter.doShare(this, image_url)
            }
            R.id.btn_window -> {
                showProgressDialog("正在设置壁纸,请稍后...")
                Glide.with(this)
                        .asBitmap()
                        .load(image_url)
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                                manager?.setBitmap(resource)
                                hideProgressDialog()
                                toast("设置完成")
                            }

                        })
            }
        }
    }

    override fun onScrollChanged(scrollView: ParallaxScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
        image.scrollTo(x, -y / 3)
    }
}
