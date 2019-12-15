package com.softartch_lib.utility

import android.app.Activity
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun hideKeyboard(window: Window) {
    val imm = window.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
}