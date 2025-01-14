import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homeworkstbc.CardType
import java.util.UUID

class CardViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>(mutableListOf(
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
    ))
    val cards: LiveData<List<Card>> = _cards

    fun addCard(card: Card) {
        val updatedList = _cards.value.orEmpty().toMutableList()
        updatedList.add(card)
        _cards.value = updatedList
    }

    fun deleteCard(cardId: String) {
        val updatedList = _cards.value.orEmpty().filter { it.id != cardId }
        _cards.value = updatedList
    }

    override fun onCleared() {
        super.onCleared()
    }
}
