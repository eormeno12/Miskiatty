package com.listen.to.miskiatty.viewmodel

import android.graphics.Bitmap
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter

@BindingAdapter("srcBitmap")
fun loadImageBitmap(imageView: ImageView, bitmap: Bitmap?){
    imageView.setImageBitmap(bitmap)
}

@InverseBindingAdapter(attribute = "android:text")
fun EditText.getStringFromBinding(): String = text.toString()