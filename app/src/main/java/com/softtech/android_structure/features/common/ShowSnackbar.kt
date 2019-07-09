package com.softtech.android_structure.features.common

import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.softtech.android_structure.R
import kotlinx.android.synthetic.main.layout_snackbar.view.*


fun showSnackbarWithDismissAction(view: View, message: String, dismissRunnable: Runnable? = null) {
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
    snackBar.setAction(R.string.dismiss) {
        snackBar.dismiss()
        dismissRunnable?.run()
    }
    snackBar.show()
}

fun showSnackbarWithDismissAction(view: View, @StringRes message: Int, dismissRunnable: Runnable? = null) {
    showSnackbarWithDismissAction(view, view.context.getString(message), dismissRunnable)
}

fun showSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .show()
}

fun showSnackbar(view: View, @StringRes message: Int) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .show()
}

fun showSnackbarErrorWithDismissAction(view: View, message: String, dismissRunnable: Runnable? = null) {
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
    snackBar.setAction(R.string.dismiss) {
        snackBar.dismiss()
        dismissRunnable?.run()
    }
    setSnackbarErrorColors(snackBar)
    snackBar.show()
}

fun showSnackbarErrorWithDismissAction(view: View, @StringRes message: Int, dismissRunnable: Runnable? = null) {
    showSnackbarErrorWithDismissAction(view, view.context.getString(message), dismissRunnable)
}

fun showErrorSnackbar(view: View, message: String) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    setSnackbarErrorColors(snackbar)
    snackbar.show()
}

fun showErrorSnackbar(view: View, @StringRes message: Int) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    setSnackbarErrorColors(snackbar)
    snackbar.show()
}

private fun setSnackbarErrorColors(snackbar: Snackbar) {
    snackbar.view.setBackgroundColor(ContextCompat.getColor(snackbar.context, android.R.color.holo_red_light))
    snackbar.view.snackbar_text.setTextColor(ContextCompat.getColor(snackbar.context, R.color.colorWhite))
}