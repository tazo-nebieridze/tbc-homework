import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.GameItemBinding

data class GameCell(val position: Int, val symbol: String)

class GameBoardDiffUtil : DiffUtil.ItemCallback<GameCell>() {

    override fun areItemsTheSame(oldItem: GameCell, newItem: GameCell): Boolean {
        return oldItem.position == newItem.position
    }

    override fun areContentsTheSame(oldItem: GameCell, newItem: GameCell): Boolean {
        return oldItem == newItem
    }
}

class GameBoardAdapter(
    private val onCellClick: (Int) -> Unit
) : ListAdapter<GameCell, GameBoardAdapter.ViewHolder>(GameBoardDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GameItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(val binding: GameItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cell: GameCell, position: Int) {
            binding.gameCellButton.setOnClickListener {
                onCellClick(position)
            }
            if (cell.symbol == "X") {
                binding.gameCellButton.setImageResource(R.drawable.baseline_close_24)
            } else if (cell.symbol == "O") {
                binding.gameCellButton.setImageResource(R.drawable.baseline_circle_24)
            } else {
                binding.gameCellButton.setImageResource(R.drawable.baseline_crop_5_4_24)
            }

            binding.gameCellButton.isEnabled = cell.symbol.isEmpty()
        }
    }
}
