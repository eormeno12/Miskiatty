package com.listen.to.miskiatty.viewmodel

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("srcUri")
fun loadImageUri(imageView: ImageView, uri: String?){
    uri?.let {
        Picasso.with(imageView.context).load(Uri.parse(it)).resize(0, 200).into(imageView)
    }
}

@BindingAdapter("backgroundColor")
fun setLinearLayoutBGColor(layout: LinearLayoutCompat, color: Int?){
    if(color != null)
        layout.background = ColorDrawable(color)
}

@InverseBindingAdapter(attribute = "android:text")
fun EditText.getStringFromBinding(): String = text.toString()
