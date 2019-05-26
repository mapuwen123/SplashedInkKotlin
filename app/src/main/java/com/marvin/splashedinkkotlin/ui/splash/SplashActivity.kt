package com.marvin.splashedinkkotlin.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.marvin.splashedinkkotlin.MyApplication
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.base.BaseRetrofit
import com.marvin.splashedinkkotlin.common.BuildConfig
import com.marvin.splashedinkkotlin.network.NetWorkService
import com.marvin.splashedinkkotlin.ui.main.MainActivity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.backgroundResource
import zlc.season.rxdownload3.core.DownloadConfig

class SplashActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val openTime = System.currentTimeMillis()

        img_splash.backgroundResource = R.drawable.splash

        val retrofitObs = Observable.just(MyApplication.context).map {
            val retrofit = BaseRetrofit.getRetrofit()
            NetWorkService.retrofitService = retrofit!!.create(NetWorkService::class.java)
            return@map 1
        }.subscribeOn(Schedulers.newThread())

        val rxDownloadObs = Observable.just(MyApplication.context).map {
            val builder = DownloadConfig.Builder.create(it)
                    .enableAutoStart(true)
                    .enableService(true)
                    .enableNotification(true)
            DownloadConfig.init(builder)
            return@map 1
        }.subscribeOn(Schedulers.newThread())

        val loggerObs = Observable.just(MyApplication.context).map {
            Logger.addLogAdapter(object : AndroidLogAdapter() {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return BuildConfig.isDebug
                }
            })
            return@map 1
        }.subscribeOn(Schedulers.newThread())

        var initSuccessNum = 0
        val initObs = Observable.merge(retrofitObs, rxDownloadObs, loggerObs)
        initObs.filter {
            initSuccessNum += it
            return@filter initSuccessNum == 3
        }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    var diffValue = System.currentTimeMillis() - openTime
                    if (diffValue < 2000) {
                        diffValue = 2000 - diffValue
                        Handler().postDelayed({
                            run {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }, diffValue)
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
    }
}
