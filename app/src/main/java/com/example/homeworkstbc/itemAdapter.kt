import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.MainActivity
import com.example.homeworkstbc.MainFragment
import com.example.homeworkstbc.databinding.ItemCardBinding


class ItemsDiffUtil : DiffUtil.ItemCallback<MainActivity.Location>() {

    override fun areItemsTheSame(oldItem: MainActivity.Location, newItem: MainActivity.Location): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: MainActivity.Location,
        newItem: MainActivity.Location
    ): Boolean {
        return oldItem == newItem
    }

}


class ItemAdapter (
    private val onOpenEdit: ( Int ) -> Unit,
)
    : ListAdapter<MainActivity.Location, ItemAdapter.ItemViewHolder>(ItemsDiffUtil()) {

    private var selectedPosition: Int = -1

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


        fun onBind(item: MainActivity.Location, position: Int) {
            binding.locationName.text = item.name
            binding.locationAddress.text = item.location
            binding.iconLocation.setImageResource(item.icon)


            binding.radioButton.isChecked = position == selectedPosition

            binding.radioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val previousPosition = selectedPosition

                    selectedPosition = getAdapterPosition()
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                }
            }
            binding.radioButton.isChecked = position == selectedPosition


            if(binding.radioButton.isChecked) {
                binding.editButton.setOnClickListener {
                        onOpenEdit(item.id)
                }
            }



        }
    }
}

