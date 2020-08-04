package com.marvin.splashedinkkotlin.utils

import android.content.Context
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 *
 * @ClassName:      ThreadUtils
 * @Description:
 * @Author:         mapw
 * @CreateDate:     2020/7/27 13:24
 */
object ThreadUtils {
    fun getThreadPool(): ExecutorService = Executors.newFixedThreadPool(3)
}

fun Context.execute(block: () -> Unit) {
    ThreadUtils.getThreadPool().execute(Runnable(block))
}