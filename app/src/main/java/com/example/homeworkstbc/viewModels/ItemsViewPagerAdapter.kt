package com.example.homeworkstbc.adapters

import ItemsDto
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkstbc.databinding.ItemCardBinding

class ItemsViewPagerAdapter(
    private val items: List<ItemsDto>
) : RecyclerView.Adapter<ItemsViewPagerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemsDto) {
            Glide.with(binding.ivCover.context)
                .load(item.cover)
                .into(binding.ivCover)

            binding.tvLocation.text = item.location
            binding.tvReactionCount.text = item.reactionCount.toString()
            binding.tvTitle.text = item.title
            binding.tvPrice.text = item.price
            binding.ratingBar.rating = item.rate?.toFloat() ?: 0f
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
