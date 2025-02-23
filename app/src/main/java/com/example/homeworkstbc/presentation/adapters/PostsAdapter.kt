import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.PostRecyclerForNoImageBinding
import com.example.homeworkstbc.databinding.PostRecyclerForOneImageBinding
import com.example.homeworkstbc.databinding.PostRecyclerForThreeImagesBinding
import com.example.homeworkstbc.databinding.PostRecyclerForTwoImagesBinding
import com.example.homeworkstbc.domain.entities.Post


class PostsDiffUtil : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem.id == newItem.id
    }

}


class PostsAdapter (
)
    : ListAdapter<Post, RecyclerView.ViewHolder>(PostsDiffUtil()) {

    companion object{
        const val NO_IMAGE = 1
        const val ONE_IMAGE = 2
        const val TWO_IMAGE = 3
        const val THREE_IMAGE = 4

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == NO_IMAGE)
            NoImageViewHolder(
                PostRecyclerForNoImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else if (viewType == ONE_IMAGE)

            OneImageViewHolder(
                PostRecyclerForOneImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else if ( viewType == TWO_IMAGE )
            TwoImageViewHolder(
                PostRecyclerForTwoImagesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            ThreeImageViewHolder(
                PostRecyclerForThreeImagesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    }

    override fun getItemViewType(position: Int): Int {

        return if (getItem(position).imagesCount == 0)
            NO_IMAGE
        else if(getItem(position).imagesCount == 1)
            ONE_IMAGE
        else if(getItem(position).imagesCount == 2)
            TWO_IMAGE
        else THREE_IMAGE

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if ( holder is NoImageViewHolder ){

            holder.onBind(position)

        } else if ( holder is OneImageViewHolder ){

            holder.onBind(position)

        } else if ( holder is TwoImageViewHolder ){

            holder.onBind(position)

        } else if ( holder is ThreeImageViewHolder ){

            holder.onBind(position)

        }


    }



    inner class NoImageViewHolder(private val binding: PostRecyclerForNoImageBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind( position: Int) {
            val item = getItem(position)
            Glide.with(binding.profileImage.context)
                .load(item.owner.profile)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.profileImage)

            Glide.with(binding.commentImage.context)
                .load(item.owner.profile)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.commentImage)

            binding.likes.text = "${item.likes} Likes"
            binding.comments.text = "${item.comments} Comments"
            binding.createAt.text = "${item.owner.postDate}"
            binding.userName.text = item.owner.fullName
            binding.postText.text = item.title
        }

    }

    inner class OneImageViewHolder(private val binding: PostRecyclerForOneImageBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind( position: Int) {
            val item = getItem(position)
            Glide.with(binding.profileImage.context)
                .load(item.owner.profile)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.profileImage)

            Glide.with(binding.commentImage.context)
                .load(item.owner.profile)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.commentImage)
            Glide.with(binding.image.context)
                .load(item.images?.get(0))
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image)

            binding.likes.text = "${item.likes} Likes"
            binding.comments.text = "${item.comments} Comments"
            binding.createAt.text = "${item.owner.postDate}"
            binding.userName.text = item.owner.fullName
            binding.postText.text = item.title
        }

    }
    inner class TwoImageViewHolder(private val binding: PostRecyclerForTwoImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind( position: Int) {
            val item = getItem(position)
            Glide.with(binding.profileImage.context)
                .load(item.owner.profile)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.profileImage)

            Glide.with(binding.commentImage.context)
                .load(item.owner.profile)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.commentImage)
            Glide.with(binding.image1.context)
                .load(item.images?.get(0))
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image1)
            Glide.with(binding.image2.context)
                .load(item.images?.get(1))
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image2)

            binding.likes.text = "${item.likes} Likes"
            binding.comments.text = "${item.comments} Comments"
            binding.createAt.text = "${item.owner.postDate}"
            binding.userName.text = item.owner.fullName
            binding.postText.text = item.title
        }
        }


    inner class ThreeImageViewHolder(private val binding: PostRecyclerForThreeImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind( position: Int) {
            val item = getItem(position)
            Glide.with(binding.profileImage.context)
                .load(item.owner.profile)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.profileImage)

            Glide.with(binding.commentImage.context)
                .load(item.owner.profile)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.commentImage)
            Glide.with(binding.image1.context)
                .load(item.images?.get(0))
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image1)
            Glide.with(binding.image2.context)
                .load(item.images?.get(1))
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image2)
            Glide.with(binding.image3.context)
                .load(item.images?.get(2))
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image3)

            binding.likes.text = "${item.likes} Likes"
            binding.comments.text = "${item.comments} Comments"
            binding.createAt.text = "${item.owner.postDate}"
            binding.userName.text = item.owner.fullName
            binding.postText.text = item.title
        }

    }
}
