import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.UserRecyclerBinding
import com.example.homeworkstbc.dto.UserDto
import com.google.common.io.Resources


class UserDiffCallback  : DiffUtil.ItemCallback<UserDto>() {
    override fun areItemsTheSame(oldItem: UserDto, newItem: UserDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserDto, newItem: UserDto): Boolean {
        return oldItem.lastName == newItem.lastName
    }


}

class ItemAdapter(
) : ListAdapter<UserDto, ItemAdapter.ViewHolder>(UserDiffCallback ()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserRecyclerBinding.inflate(
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

    inner class ViewHolder(private val binding: UserRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserDto, position: Int) {

            binding.userName.text = "${item.firstName} ${item.lastName}"
            val context = binding.root.context

            when (item.activationStatus.toInt()) {
                in Int.MIN_VALUE..0 -> binding.userActiveStatus.text = context.getString(R.string.user_inactive)
                1 -> binding.userActiveStatus.text = context.getString(R.string.user_online)
                2 -> binding.userActiveStatus.text = context.getString(R.string.user_active_minutes_ago)
                in 3..22 -> binding.userActiveStatus.text = context.getString(R.string.user_active_hours_ago)
                else -> binding.userActiveStatus.text = context.getString(R.string.user_active_long_time_ago)
            }

            Glide.with(binding.userAvatar.context)
                .load(item.avatar)
                .placeholder(R.drawable.picture)
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.userAvatar)


        }


    }
} 