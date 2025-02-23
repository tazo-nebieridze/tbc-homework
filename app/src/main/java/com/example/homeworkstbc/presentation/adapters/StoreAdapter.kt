package com.example.homeworkstbc.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.StoresRecyclerBinding
import com.example.homeworkstbc.domain.entities.Store


class ItemsDiffUtil : DiffUtil.ItemCallback<Store>() {

    override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Store,
        newItem: Store
    ): Boolean {
        return oldItem == newItem
    }

}


class StoreAdapter (
)
    : ListAdapter<Store, StoreAdapter.ItemViewHolder>(ItemsDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            StoresRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item, position)


    }



    inner class ItemViewHolder(private val binding: StoresRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind(item: Store, position: Int) {
            Glide.with(binding.storeImage.context)
                .load(item.cover)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.storeImage)
            binding.storeTitle.text = item.title
        }

        }
    }

