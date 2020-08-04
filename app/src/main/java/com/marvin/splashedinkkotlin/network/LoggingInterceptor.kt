package com.marvin.splashedinkkotlin.network

import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.hasBody
import okhttp3.internal.http.promisesBody
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * 网络日志抓取
 *
 * @ClassName: LoggingInterceptor
 * @CreateDate: 2020/1/3/003 11:07
 * @Author: MaPuwen
 */
class LoggingInterceptor : Interceptor {
    private val UTF8 = Charset.forName("UTF-8")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
//        val urls = request.url.toString().split("ccmm".toRegex()).toTypedArray()
//        if (urls.size > 1) {
        Logger.i("客户端发送：url：${request.url}")
        //            LogOutputUtils.writeLog(String.format("客户端发送：url：%s", urls[1]));
//        }
        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val responseBody = response.body
        var rBody: String? = null
        if (response.promisesBody()) {
            val source = responseBody!!.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer()
            var charset = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            rBody = buffer.clone().readString(charset!!)
        }
        Logger.i("服务器返回：${response.code}${response.message} ${tookMs}ms url：${request.url}-响应body：$rBody")
        //        LogOutputUtils.writeLog(String.format("服务器返回：%s%s %sms url：%s-响应body：%s",
//                response.code(), response.message(), tookMs, responseUrls[1], rBody));
        return response.newBuilder().build()
    }
}