package com.marvin.splashedinkkotlin.utils.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.module.GlideModule

import com.bumptech.glide.load.engine.cache.DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE
import com.marvin.splashedinkkotlin.common.BuildConfig

/**
 * Created by Administrator on 2017/7/26.
 */
class MyGlideModule : GlideModule {
    override fun registerComponents(context: Context?, glide: Glide?, registry: Registry?) {
        OkHttpLibraryGlideModule().registerComponents(context, glide, registry)
    }

    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        if (builder != null) {
            builder.setDiskCache(DiskLruCacheFactory(BuildConfig.image_cache, DEFAULT_DISK_CACHE_SIZE.toLong()))
        }
    }
}