package com.listen.to.miskiatty.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter

@BindingAdapter("srcBitmap")
fun loadImageBitmap(imageView: ImageView, bitmap: Bitmap?){
    imageView.setImageBitmap(bitmap)
}

@BindingAdapter("backgroundColor")
fun setLinearLayoutBGColor(layout: LinearLayoutCompat, color: Int?){
    if(color != null)
        layout.background = ColorDrawable(color)
}

@InverseBindingAdapter(attribute = "android:text")
fun EditText.getStringFromBinding(): String = text.toString()