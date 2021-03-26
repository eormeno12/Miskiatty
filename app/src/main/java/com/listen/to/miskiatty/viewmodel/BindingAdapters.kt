package com.listen.to.miskiatty.viewmodel

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("srcBitmap")
fun loadImageBitmap(imageView: ImageView, bitmap: Bitmap?){
    imageView.setImageBitmap(bitmap)
}