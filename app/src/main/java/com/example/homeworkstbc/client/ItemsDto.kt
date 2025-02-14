import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemsDto(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val location: String,
    @SerialName("reaction_count")
    val reactionCount: Int,
    val rate: Int?
)
