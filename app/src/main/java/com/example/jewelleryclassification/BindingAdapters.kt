package com.example.jewelleryclassification

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
import com.example.jewelleryclassification.database.JWImage
import com.example.jewelleryclassification.overview.PhotoGridAdapter
import com.example.jewelleryclassification.types.TypesGridAdapter
import uk.co.senab.photoview.PhotoView

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data : List<JWImage>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("typeData")
fun bindTypeRecyclerView(recyclerView: RecyclerView, data : List<JWImage>?) {
    val adapter = recyclerView.adapter as TypesGridAdapter
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

@BindingAdapter("photoViewImageUri")
fun bindPhotoView(photoView : PhotoView, imgUri: String?) {
    imgUri?.let {
        val uri = it.toUri()
        photoView.setImageURI(uri)
    }
}

@BindingAdapter("jwType")
fun bindTextView(textView: TextView, type: String) {
    type?.let {
        when (type) {
            "0" -> textView.text = "DKP"
            "1" -> textView.text = "Jhumki"
            "2" -> textView.text = "Ladies Ring"
            "3" -> textView.text = "M Chain"
            "4" -> textView.text = "SKP"
            "5" -> textView.text = "Set"
            else -> textView.text = "Not yet Classified"
        }
    }
}
