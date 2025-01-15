import androidx.lifecycle.ViewModel
import com.example.homeworkstbc.CardType
import java.util.UUID

class CardViewModel : ViewModel() {

    private val cards = mutableListOf(
        Card(
            UUID.randomUUID().toString(),
            "Tamazi nebieridze",
            "1111222233334444",
            777,
            "10",
            "27",
            CardType.VISA
        ),
        Card(
            UUID.randomUUID().toString(),
            "Abtuna sixarulidze",
            "7777777777777777",
            123,
            "11",
            "28",
            CardType.MASTERCARD
        ),
        Card(
            UUID.randomUUID().toString(),
            "Donald Trump",
            "9999888877775555",
            987,
            "12",
            "99",
            CardType.VISA
        )
    )

    fun getCards(): List<Card> = cards

    fun addCard(card: Card) {
        cards.add(card)
    }

    fun deleteCard(cardId: String) {
        cards.removeAll { it.id == cardId }
    }
}
