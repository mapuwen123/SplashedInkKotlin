package com.marvin.splashedinkkotlin.common

import com.marvin.splashedinkkotlin.utils.SDCardUtil

/**
 * Created by Administrator on 2017/7/28.
 */

object BuildConfig {
    val AppDir = SDCardUtil.getSDCardPath() + "/SplashedInkKotlin"
    val isDebug = true
}
