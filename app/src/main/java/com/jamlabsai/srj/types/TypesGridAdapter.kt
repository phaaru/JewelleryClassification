package com.jamlabsai.srj.types

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jamlabsai.srj.database.JWImage
import com.jamlabsai.srj.databinding.GridViewTypeBinding

class TypesGridAdapter(val onClickListener: OnClickListener) : ListAdapter<JWImage, TypesGridAdapter.JWTypeViewHolder>(DiffCallBack) {

    class JWTypeViewHolder(private var binding: GridViewTypeBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JWTypeViewHolder {
        return JWTypeViewHolder(GridViewTypeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: JWTypeViewHolder, position: Int) {
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