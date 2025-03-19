package com.example.homeworkstbc.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.databinding.CategoryRecyclerItemBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.homeworkstbc.presentation.models.Category




object CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}
class CategoryAdapter : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(private val binding: CategoryRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.apply {
                val categoryNameTextView = root.findViewById<androidx.appcompat.widget.AppCompatTextView>(
                    com.example.homeworkstbc.R.id.categoryName
                )
                categoryNameTextView.text = category.name

                val ballViews = listOf(ball1, ball2, ball3, ball4)
                ballViews.forEachIndexed { index, ball ->
                    ball.visibility = if (index < category.parentCount) View.VISIBLE else View.GONE
                }
            }
        }
    }
}