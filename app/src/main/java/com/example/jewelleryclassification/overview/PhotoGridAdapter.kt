package com.example.jewelleryclassification.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jewelleryclassification.database.JWImage
import com.example.jewelleryclassification.databinding.GridViewItemBinding


class PhotoGridAdapter(val onClickListener: OnClickListener) : ListAdapter<JWImage, PhotoGridAdapter.JWImageViewHolder>(DiffCallBack) {

    class JWImageViewHolder(private var binding: GridViewItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(jwImage: JWImage) {
            binding.jwimage = jwImage
            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<JWImage>() {
        override fun areItemsTheSame(oldItem: JWImage, newItem: JWImage): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: JWImage, newItem: JWImage): Boolean {
            return oldItem.imageId == newItem.imageId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JWImageViewHolder {
        return JWImageViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: JWImageViewHolder, position: Int) {
        val jwImage = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(jwImage)
        }
        holder.bind(jwImage)
    }

    class OnClickListener(val clickListener: (jwImage:JWImage) -> Unit) {
        fun onClick(jwImage: JWImage) = clickListener(jwImage)
    }
}