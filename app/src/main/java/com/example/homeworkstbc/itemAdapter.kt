import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.MainActivity
import com.example.homeworkstbc.MainFragment
import com.example.homeworkstbc.Message
import com.example.homeworkstbc.R
import com.example.homeworkstbc.SenderType
import com.example.homeworkstbc.databinding.MyMessagesBinding
import com.example.homeworkstbc.databinding.OtherMessagesBinding
import java.util.Date


class ItemsDiffUtil : DiffUtil.ItemCallback<Message>() {

    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Message,
        newItem: Message
    ): Boolean {
        return oldItem.id == newItem.id
    }

}


class ItemAdapter (
    private val formatDate : (Date) -> String
)
    : ListAdapter<Message, RecyclerView.ViewHolder>(ItemsDiffUtil()) {

        companion object{
            const val CURRENT_USER = 1
            const val OTHER_USER = 2
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == CURRENT_USER)
            CurrentViewHolder(
                MyMessagesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                false
                )
            )
                else

            OtherViewHolder(
                OtherMessagesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun getItemViewType(position: Int): Int {

        return if (getItem(position).sender == SenderType.CURRENT)
            CURRENT_USER
        else
            OTHER_USER

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if ( holder is CurrentViewHolder ){

            holder.onBind(position)

        } else if ( holder is OtherViewHolder ){

            holder.onBind(position)

        }


    }



    inner class CurrentViewHolder(private val binding: MyMessagesBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind( position: Int) {
            val item = getItem(position)
            val formattedDate = formatDate(item.date)

            binding.myText.text = item.text

            binding.date.text = formattedDate.toString()
        }

    }

    inner class OtherViewHolder(private val binding: OtherMessagesBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind( position: Int) {
            val item = getItem(position)
            val formattedDate = formatDate(item.date)

            binding.otherText.text = item.text
            binding.date.text = formattedDate.toString()
        }

    }
}

