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
            "0" -> textView.text = "Bangles"
            "1" -> textView.text = "DKP"
            "2" -> textView.text = "Fox Kanthi"
            "3" -> textView.text = "Jhumki"
            "4" -> textView.text = "Ladies Ring"
            "5" -> textView.text = "Mangal Sutra"
            "6" -> textView.text = "Nath"
            "7" -> textView.text = "SKP"
            "8" -> textView.text = "Set"
            "9" -> textView.text = "Thrissur Kerela"
            "10" -> textView.text = "Tika"
            "11" -> textView.text = "Toda"
            else -> textView.text = "Not yet Classified"
        }
    }
}
