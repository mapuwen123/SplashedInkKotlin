package com.marvin.splashedinkkotlin.utils.download

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

fun execute(block: () -> Unit) {
    ThreadUtils.getThreadPool().execute(Runnable(block))
}