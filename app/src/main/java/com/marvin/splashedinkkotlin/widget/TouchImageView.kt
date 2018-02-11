package com.marvin.splashedinkkotlin.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

/**
 * Created by Marvin on 2018/1/29.
 */

@SuppressLint("AppCompatCustomView")
class TouchImageView : ImageView {
    var beginX: Int = 0
    var beginY: Int = 0

    var lastX: Int = 0
    var lastY: Int = 0

    var offsetX: Int = 0
    var offsetY: Int = 0

    lateinit var mContext: Context

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context,
                attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    fun init(context: Context) {
        this.mContext = context
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 获取当前触摸的绝对坐标
        var rawX: Int = event?.rawX?.toInt() ?: 0
        var rawY: Int = event?.rawY?.toInt() ?: 0
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                // 上一次离开时的坐标
                lastX = rawX
                lastY = rawY

                beginX = event.rawX.toInt()
                beginY = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                // 两次的偏移量
                offsetX = rawX - lastX
                offsetY = rawY - lastY
                (parent as View).scrollBy(-offsetX, -offsetY)
                // 不断修改上次移动完成后坐标
                lastX = rawX
                lastY = rawY
            }
            MotionEvent.ACTION_UP -> {
                var diffX = Math.abs(beginX - event.rawX.toInt())
                var diffY = Math.abs(beginY - event.rawY.toInt())
                if (diffX > 500 || diffY > 500)
                    (this.mContext as Activity).onBackPressed()
                else
                    (parent as View).scrollTo(0, 0)
            }
            else -> {
            }
        }
        return true
    }
}
