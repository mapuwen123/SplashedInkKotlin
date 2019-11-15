package com.marvin.splashedinkkotlin.network

import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  作者：MaPuWen
 *  时间：2018/12/17 20:07
 *  邮箱：mapuwen@outlook.com
 */
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        Request request = chain.request();
//
//        long t1 = System.nanoTime();
//        logger.info(String.format("Sending request %s on %s%n%s",
//                request.url(), chain.connection(), request.headers()));
//
//        Response response = chain.proceed(request);
//
//        long t2 = System.nanoTime();
//        logger.info(String.format("Received response for %s in %.1fms%n%s",
//                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        val request = chain.request()
        val t1 = System.nanoTime()
        Logger.i(String.format("请求 %s on %s%n%s",
                request.url, chain.connection(), request.headers))
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        Logger.i(String.format("返回 for %s in %.1fms%n%s",
                response.request.url, (t2 - t1) / 1e6, response.body?.string()))
        return response
    }
}