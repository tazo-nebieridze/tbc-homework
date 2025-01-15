import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.CardType
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.CardRecyclerBinding

class DiffUtil1 : DiffUtil.ItemCallback<Card>() {

    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}

class CardAdapter(
    private val longClickListener: (String) -> Unit
) : ListAdapter<Card,CardAdapter.CardViewHolder>(DiffUtil1()) {

    inner class CardViewHolder(private val binding: CardRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            binding.cardholderNameValue.text = card.holder
            binding.cardNumber.text = card.cardNumber
            binding.validThruDate.text = "${card.expiryMonth}/${card.expiryYear}"

            val cardLogo = when (card.cardType) {
                CardType.VISA -> R.drawable.media1
                CardType.MASTERCARD -> R.drawable.media
            }
            binding.visaLogo.setImageResource(cardLogo)

            binding.root.setOnLongClickListener {
                longClickListener(card.id)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
}
