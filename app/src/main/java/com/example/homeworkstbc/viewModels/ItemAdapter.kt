import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.RecyclerItemBinding

class ItemAdapter(
    private val items: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(items[position])


    }

    inner class ItemViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: String) {
            binding.root.setOnClickListener {
                onItemClick(item)
            }
            when (item) {
                "f" -> {
                    binding.symbol.text = ""
                    binding.symbol.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_fingerprint_24, 0, 0, 0
                    )
                }
                "d" -> {
                    binding.symbol.text = ""
                    binding.symbol.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_backspace_24, 0, 0, 0
                    )
                }
                else -> {
                    binding.symbol.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    binding.symbol.text = item
                }
            }
        }
    }
}
