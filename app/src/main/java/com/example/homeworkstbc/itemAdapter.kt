import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkstbc.MainActivity
import com.example.homeworkstbc.MainFragment
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.RecyclerItemBinding
import com.example.homeworkstbc.models.Chat


class ItemsDiffUtil : DiffUtil.ItemCallback<Chat>() {

    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Chat,
        newItem: Chat
    ): Boolean {
        return oldItem.id == newItem.id
    }

}


class ItemAdapter (
)
    : ListAdapter<Chat, ItemAdapter.ItemViewHolder>(ItemsDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            RecyclerItemBinding.inflate(
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



    inner class ItemViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind(item: Chat, position: Int) {

            binding.messageName.text = item.owner
            binding.messageText.text = item.lastMessage
            binding.messageTime.text = item.lastActive
            Glide.with(binding.messageImage.context)
                .load(item.image)
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture)
                .into(binding.messageImage)
                binding.unreadMessages.text = item.unreadMessages.toString()


        }
    }
}
