import com.example.homeworkstbc.domain.entities.Owner
import com.example.homeworkstbc.domain.entities.Post
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PostMapper {

    fun mapToPresentation(postsDto: PostsDto): Post {
        val imagesCount = postsDto.images?.size ?: 0
        val owner = mapOwnerToPresentation(postsDto.owner)
        return Post(
            id = postsDto.id,
            images = postsDto.images,
            title = postsDto.title,
            comments = postsDto.comments,
            likes = postsDto.likes,
            shareContent = postsDto.shareContent,
            owner = owner,
            imagesCount = imagesCount
        )
    }


    private fun mapOwnerToPresentation(ownerDto: OwnerDto): Owner {
        val formattedDate = formatEpochTime(ownerDto.postDate)
        return Owner(
            fullName = ownerDto.firstName + " " + ownerDto.lastName,
            profile = ownerDto.profile,
            postDate = formattedDate
        )
    }

    private fun formatEpochTime(epoch: Long): String {
        val date = Date(epoch * 1000)
        val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return format.format(date)
    }
}