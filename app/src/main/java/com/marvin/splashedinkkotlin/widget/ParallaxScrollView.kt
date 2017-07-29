package com.marvin.splashedinkkotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * Created by Administrator on 2017/7/13.
 * 视差滚动
 */

class ParallaxScrollView : ScrollView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    private var mScrollViewListener: ScrollviewListener? = null

    fun setScrollViewListener(listener: ScrollviewListener) {
        this.mScrollViewListener = listener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (mScrollViewListener != null) {
            mScrollViewListener!!.onScrollChanged(this, l, t, oldl, oldt)
        }
    }

    interface ScrollviewListener {
        fun onScrollChanged(scrollView: ParallaxScrollView, x: Int, y: Int, oldx: Int, oldy: Int)
    }
}
