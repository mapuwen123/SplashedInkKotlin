package com.marvin.splashedinkkotlin.base

import android.text.TextUtils
import com.marvin.splashedinkkotlin.common.APIConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Marvin
 */

abstract class BaseRetrofit : RuntimeException() {
    companion object {

        private val mHttpClientBuilder = OkHttpClient.Builder()
        private var mRetrofit: Retrofit? = null


        fun getRetrofit(): Retrofit? {
            if (mRetrofit == null) {

                //设定30秒超时,拦截http请求进行监控重写或重试,打印网络请求
                mHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(interceptor)
                val mOkHttpClient = mHttpClientBuilder.build()


                //构建Retrofit
                mRetrofit = Retrofit.Builder()//配置服务器路径
                        .baseUrl(APIConfig.HOST)
                        //配置转化库，默认是Gson
                        .addConverterFactory(GsonConverterFactory.create())
                        //配置回调库，采用RxJava
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        //设置OKHttpClient为网络客户端
                        .client(mOkHttpClient)
                        .build()
            }
            return mRetrofit
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
    }

}
