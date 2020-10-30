package com.marvin.splashedinkkotlin.network.download

import android.text.TextUtils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.marvin.splashedinkkotlin.common.APIConfig
import com.marvin.splashedinkkotlin.db.AppDataBase
import com.marvin.splashedinkkotlin.db.entity.PhotoEntity
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class DownloadManager : DownloadProgressListener {
    private var mRetrofit: Retrofit? = null
    private var mFileSaveUtils: FileSaveUtils? = null
    private val mHttpClientBuilder = OkHttpClient.Builder()
    var photoEntity: PhotoEntity? = null

    companion object {
        const val statusSuccess = 0
        const val statusError = 1
        const val statusStart = 2
        const val statusStop = 3
    }

    private var interceptor = { chain: Interceptor.Chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        var cacheControl = request.cacheControl.toString()
        if (TextUtils.isEmpty(cacheControl)) {
            cacheControl = "public, max-age=60"
        }
        response.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma")
                .build()
    }

    fun start(photoId: String, url: String) {
        //设定10分钟超时,拦截http请求进行监控重写或重试,打印网络请求
        mHttpClientBuilder.connectTimeout(60 * 10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)

        val mOkHttpClient = mHttpClientBuilder.build()

        //构建Retrofit
        mRetrofit = Retrofit.Builder()//配置服务器路径
                .baseUrl(APIConfig.HOST)
                //配置协程回调库
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                //设置OKHttpClient为网络客户端
                .client(mOkHttpClient)
                .build()

        DownloadService.retrofitService = mRetrofit!!.create(DownloadService::class.java)

        mFileSaveUtils = FileSaveUtils(this)

        runBlocking(Dispatchers.Default) {
            photoEntity = AppDataBase.db.photoDao().selectByPhotoId(photoId)
        }
        if (photoEntity == null) {
            photoEntity = PhotoEntity(photoId,
                    "$photoId.jpeg",
                    0,
                    0,
                    url,
                    statusStart)
            runBlocking(Dispatchers.Default) {
                AppDataBase.db.photoDao().insert(photoEntity!!)
            }
            DownloadQueues.instance.enqueueTask(this)
        } else {
            when (photoEntity?.status) {
                statusSuccess, statusStart -> Logger.i("当前任务已在队列中")
                statusError -> {
                    photoEntity?.readLength = 0
                    photoEntity?.status = statusStart
                    DownloadQueues.instance.enqueueTask(this)
                }
                statusStop -> {
                    photoEntity?.status = statusStart
                    DownloadQueues.instance.enqueueTask(this)
                }
            }
        }
    }

    fun download() {
        GlobalScope.launch {
            try {
                val responseBody =
                        photoEntity?.url?.let {
                            DownloadService.retrofitService.download("bytes=${photoEntity?.readLength}-", it)
                        }
                onStart()
                responseBody?.let { response ->
                    photoEntity?.photoId?.let { photoId ->
                        mFileSaveUtils?.savePhoto(photoId, response)
                    }
                }
            } catch (e: Throwable) {
                Logger.e("下载出错！")
                onError(e)
            }
        }
    }

    override fun onStart() {
        photoEntity?.status = statusStart
        runBlocking(Dispatchers.Default) {
            photoEntity?.let { AppDataBase.db.photoDao().update(it) }
        }
    }

    override fun onProgress(read: Long, contentLength: Long) {
        photoEntity?.contentLength = contentLength
        photoEntity?.readLength = read
        photoEntity?.status = statusStart
        Logger.i("$read of $contentLength")
        runBlocking(Dispatchers.Default) {
            photoEntity?.let { AppDataBase.db.photoDao().update(it) }
        }
    }

    override fun onSuccess() {
        photoEntity?.status = statusSuccess
        runBlocking(Dispatchers.Default) {
            photoEntity?.let { AppDataBase.db.photoDao().update(it) }
        }
        DownloadQueues.instance.remove(this)
    }

    override fun onError(e: Throwable) {
        photoEntity?.status = statusError
        runBlocking(Dispatchers.Default) {
            photoEntity?.let { AppDataBase.db.photoDao().update(it) }
        }
        DownloadQueues.instance.remove(this)
    }

}