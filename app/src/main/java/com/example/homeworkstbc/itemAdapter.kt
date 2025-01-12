import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.Item
import com.example.homeworkstbc.R
import com.example.homeworkstbc.StatusType
import com.example.homeworkstbc.databinding.RecyclerItemBinding
import com.google.common.io.Resources


class DiffUtil : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.feedBack == newItem.feedBack
    }
}

class ItemAdapter(
    private val showFeedbackModal :  (Item, Int) -> Unit
) : ListAdapter<Item, ItemAdapter.ViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
        println(position)
    }

    inner class ViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, position: Int) {
            binding.itemName.text = item.name
            binding.itemPrice.text = "$ ${item.totalPrice}"
            binding.itemImage.setImageResource(item.picture)
            binding.itemDetails.text = "${item.colorName} | Qty = ${item.quantity}"
            binding.itemColor.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, item.color)

            when {
                item.status == StatusType.ACTIVE -> {
                    binding.buyAgainButton.text = "Track Order"
                    binding.buyAgainButton.setOnClickListener(null)
                }
                item.status == StatusType.COMPLETED && item.feedBack == null -> {
                    binding.buyAgainButton.text = "Leave Review"
                    binding.buyAgainButton.setOnClickListener {
                        showFeedbackModal(item, position)
                    }
                }
                else -> {
                    binding.buyAgainButton.text = "Buy Again"
                    binding.buyAgainButton.setOnClickListener(null)
                }
            }
        }

    }
}
