package com.marvin.splashedinkkotlin.ui.search

import com.marvin.splashedinkkotlin.base.BaseView
import com.marvin.splashedinkkotlin.bean.SearchBean

/**
 * Created by Administrator on 2017/8/3.
 */
interface SearchView : BaseView {
    fun upData(data: SearchBean)
}