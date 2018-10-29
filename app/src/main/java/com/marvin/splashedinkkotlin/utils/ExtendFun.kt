package com.marvin.splashedinkkotlin.utils

import android.content.Context
import com.google.android.material.snackbar.Snackbar
import android.view.View

/**
 * Created by mapw on 2018/1/4.
 */
fun Context.snackbar(view: View, message: CharSequence, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}