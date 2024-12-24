import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.MainActivity

import com.example.homeworkstbc.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<MainActivity.Category>,
    private val onCategoryClick: (MainActivity.Category) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.onBind(categories[position])

    }


    inner class CategoryViewHolder( private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(category: MainActivity.Category) {

            binding.categoryItem.text = category.name
            binding.categoryItem.setOnClickListener {
                onCategoryClick(category)
            }
            binding.categoryItem.setCompoundDrawablesWithIntrinsicBounds(
                category.icon,
                0,
                0,
                0
            )

        }
    }
}
