import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.MainActivity
import com.example.homeworkstbc.databinding.ItemCardBinding

class ItemAdapter(private val items: List<MainActivity.Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    inner class ItemViewHolder( private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: MainActivity.Item) {
            binding.itemName.text = item.name
            binding.itemPrice.text = item.price
            binding.itemImage.setImageResource(item.image)
        }
    }
}
