package com.example.jewelleryclassification

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
import com.example.jewelleryclassification.database.JWImage
import com.example.jewelleryclassification.overview.PhotoGridAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data : List<JWImage>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUri")
fun bindImage(imgView: ImageView, imgUri: String?) {
    imgUri?.let {
        val uri = it.toUri()
//        Glide.with(imgView.context)
//            .load(uri)
//            .into(imgView)
        imgView.setImageURI(uri)
    }
}