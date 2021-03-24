package com.listen.to.miskiatty.viewmodel

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter

class BindingAdapters {
    @BindingAdapter("srcBitmap")
    fun loadImageBitmap(imageView: ImageView, bitmap: Bitmap?){
        bitmap?.let { imageView.setImageBitmap(it) }
    }
}