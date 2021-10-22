package com.summer.newsapp.utils

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("image")
fun loadImage(view: ShapeableImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}