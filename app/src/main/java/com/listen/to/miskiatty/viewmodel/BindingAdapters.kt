package com.listen.to.miskiatty.viewmodel

import android.graphics.drawable.ColorDrawable
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("srcUriProduct")
fun loadImageUriProduct(imageView: ImageView, url: String?) = loadImageUri(imageView, url, 200)

@BindingAdapter("srcUriClient")
fun loadImageUriClient(imageView: ImageView, url: String?) = loadImageUri(imageView, url, 40)

@BindingAdapter("srcUriClientDetails")
fun loadImageUriClientDetails(imageView: ImageView, url: String?) = loadImageUri(imageView, url, 160)


fun loadImageUri(imageView: ImageView, url: String?, height: Int) {
    url?.let {
        Picasso
            .get()
            .load(url)
            .resize(0, height)
            .into(imageView)
    }
}

@BindingAdapter("backgroundColor")
fun setLinearLayoutBGColor(layout: LinearLayoutCompat, color: Int?){
    if(color != null)
        layout.background = ColorDrawable(color)
}

@InverseBindingAdapter(attribute = "android:text")
fun EditText.getStringFromBinding(): String = text.toString()
