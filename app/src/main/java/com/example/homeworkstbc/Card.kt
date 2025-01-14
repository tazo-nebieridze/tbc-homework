import android.os.Parcelable
import com.example.homeworkstbc.CardType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card(
    val id: String,
    var holder: String,
    var cardNumber: String,
    var cvv: Int,
    var expiryMonth: String,
    var expiryYear: String,
    var cardType: CardType
) : Parcelable
