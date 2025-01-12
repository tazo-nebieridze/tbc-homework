import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homeworkstbc.ActiveFragment
import com.example.homeworkstbc.CompletedFragment
import com.example.homeworkstbc.Item

class ViewPagerAdapter(fragment: Fragment, private val items: MutableList<Item>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActiveFragment(items)
            1 -> CompletedFragment(items)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
