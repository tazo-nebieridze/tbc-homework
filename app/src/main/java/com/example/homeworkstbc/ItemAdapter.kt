import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.UserRecyclerBinding
import com.google.common.io.Resources


class UserDiffCallback  : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.lastName == newItem.lastName
    }


}

class ItemAdapter(
) : ListAdapter<User, ItemAdapter.ViewHolder>(UserDiffCallback ()) {

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

        fun bind(item: User, position: Int) {

            binding.userName.text = "${item.firstName} ${item.lastName}"
            binding.userEmail.text = item.email
            Glide.with(binding.userAvatar.context)
                .load(item.avatar)
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture)
                .into(binding.userAvatar)



        }


    }
}