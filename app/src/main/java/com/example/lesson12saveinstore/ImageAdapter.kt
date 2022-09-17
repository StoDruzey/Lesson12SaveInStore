package com.example.lesson12saveinstore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson12saveinstore.databinding.ItemImageBinding

data class Image()

class ImageViewHolder(
    private val binding: ItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {

    }

}

class ImageAdapter : ListAdapter<Image, ImageViewHolder>(DIFF_UTIL) {//needs element and viewHolder for ListAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind()
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                TODO("Not yet implemented")
            }
        }
    }
}