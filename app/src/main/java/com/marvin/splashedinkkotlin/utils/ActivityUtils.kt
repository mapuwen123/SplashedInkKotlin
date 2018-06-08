package com.marvin.splashedinkkotlin.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import org.jetbrains.anko.coroutines.experimental.asReference

import java.util.Stack

/**
 * Created by Administrator on 2017/6/10.
 */

object ActivityUtils {
    private var mActivityStack: Stack<Activity>? = null

    /**
     * 添加一个Activity到堆栈中
     * @param activity
     */
    fun addActivity(activity: Activity) {
        if (null == mActivityStack) {
            mActivityStack = Stack()
        }
        mActivityStack!!.add(activity)
    }

    /**
     * 从堆栈中移除指定的Activity
     * @param activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            mActivityStack!!.remove(activity)
        }
    }

    /**
     * 获取顶部的Activity
     * @return 顶部的Activity
     */
    val topActivity: Activity?
        get() {
            if (mActivityStack!!.isEmpty()) {
                return null
            } else {
                return mActivityStack!![mActivityStack!!.size - 1]
            }
        }

    /**
     * 结束所有的Activit
     */
    fun removeAllActivity() {
        if (mActivityStack != null && mActivityStack!!.size > 0) {
            for (activity in mActivityStack!!) {
                activity.finish()
            }
        }
    }

    /**
     * 退出应用
     */
    fun appExit(context: Context) {
        try {
            removeAllActivity()
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.restartPackage(context.packageName)
            System.exit(0)
        } catch (e: Exception) {
        }
    }
}
