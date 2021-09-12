package com.example.imagesearchapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.UnsplashImageItemBinding
import com.example.imagesearchapp.ui.main.data.UnsplashPhoto

class UnsplashPagingAdapter constructor(private val onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<UnsplashPhoto, UnsplashPagingAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            UnsplashImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { holder.bind(currentItem) }
    }

    inner class PhotoViewHolder(private val binding: UnsplashImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    val unsplashPhoto = getItem(position)
                    if(unsplashPhoto != null) {
                        onItemClickListener.onItemClick(unsplashPhoto)
                    }
                }
            }
        }

        fun bind(unsplashPhoto: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView).load(unsplashPhoto.urls.regular).centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error_24).into(ivImage)

                tvUsername.text = unsplashPhoto.user.username
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(unsplashPhoto: UnsplashPhoto)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem == newItem
        }
    }
}