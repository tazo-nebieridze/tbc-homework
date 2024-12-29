import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.MainActivity
import com.example.homeworkstbc.MainFragment
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.ItemCardBinding


class ItemsDiffUtil : DiffUtil.ItemCallback<MainActivity.Order>() {

    override fun areItemsTheSame(oldItem: MainActivity.Order, newItem: MainActivity.Order): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: MainActivity.Order,
        newItem: MainActivity.Order
    ): Boolean {
        return oldItem == newItem
    }

}


class ItemAdapter (
    private val openDetails : (Int,String) -> Unit
)
    : ListAdapter<MainActivity.Order, ItemAdapter.ItemViewHolder>(ItemsDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemCardBinding.inflate(
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



    inner class ItemViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind(item: MainActivity.Order, position: Int) {
            binding.orderNumber.text = "Order #${item.orderCount}"
            binding.createdAt.text = item.createdAt
            binding.id.text = item.id
            binding.payment.text = item.payment.toString()
            binding.quantity.text = item.itemsQuantity.toString()
            binding.orderStatus.text = item.status
            val statusColor = when (item.status) {
                "PENDING" -> R.color.pending
                "DELIVERED" -> R.color.delivered
                "CANCELLED" -> R.color.cancelled
                else -> R.color.pending
            }
            binding.orderStatus.setTextColor(ContextCompat.getColor(binding.root.context, statusColor))
            binding.btnDetails.setOnClickListener {
                openDetails(item.orderCount,item.status)
            }
        }
    }
}

