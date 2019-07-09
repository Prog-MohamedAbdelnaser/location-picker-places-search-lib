package com.softtech.android_structure.base.widget

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

fun AppCompatImageView.loadImageFromUrl(url: String, requestListener: RequestListener<Drawable>? = null) {
    Glide.with(context.applicationContext)
            .load(url)
            .listener(requestListener)
            .into(this)
}

fun AppCompatImageView.loadImageFromUrl(url: String, @DrawableRes errorPlaceHolder: Int,
                                        requestListener: RequestListener<Drawable>? = null) {
    Glide.with(context.applicationContext)
            .setDefaultRequestOptions(createRequestOptions(errorPlaceHolder))
            .load(url)
            .listener(requestListener)
            .into(this)
}

fun AppCompatImageView.loadImageFromUrl(url: String, @DrawableRes errorPlaceHolder: Int,
                                        @DrawableRes loadingPlaceHolder: Int,
                                        requestListener: RequestListener<Drawable>? = null,
                                        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.NONE) {
    Glide.with(context.applicationContext)
            .setDefaultRequestOptions(createRequestOptions(errorPlaceHolder, loadingPlaceHolder))
            .load(url)
            .diskCacheStrategy(diskCacheStrategy)
            .listener(requestListener)
            .into(this)
}

fun AppCompatImageView.cancelImageLoadingFromUrl() {
    Glide.with(context.applicationContext).clear(this)
}

private fun createRequestOptions(@DrawableRes errorPlaceHolder: Int,
                                 @DrawableRes loadingPlaceHolder: Int): RequestOptions =
        createRequestOptions(errorPlaceHolder).placeholder(loadingPlaceHolder)


private fun createRequestOptions(@DrawableRes errorPlaceHolder: Int): RequestOptions =
        RequestOptions().error(errorPlaceHolder)