import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.Input
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.RecyclerItemBinding
import com.google.common.io.Resources



class ItemAdapter(
    private val cards: InputsList,
    private val attachInputRecycler: (RecyclerItemBinding,List<Input> ) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cards.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = cards[position]
        holder.bind(currentItem, position)
    }

    inner class ViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: List<Input>, position: Int) {

            attachInputRecycler(binding,item)


        }

    }
}