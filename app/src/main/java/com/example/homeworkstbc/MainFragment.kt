package com.example.homeworkstbc

import ViewPagerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.homeworkstbc.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.UUID


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    val items = mutableListOf(
        Item(
            UUID.randomUUID().toString(),
            "Modern Wingback",
            R.color.black,
            "Black",
            280,
            R.drawable.images,
            2,
            StatusType.ACTIVE,
            null
        ),
        Item(
            UUID.randomUUID().toString(),
            "Soviet Chair",
            R.color.red,
            "Red",

            150,
            R.drawable.images,
            3,
            StatusType.COMPLETED,
            null

        ),
        Item(
            UUID.randomUUID().toString(),
            "American Chair",
            R.color.green,
            "Green",
            400,
            R.drawable.images,
            6,
            StatusType.ACTIVE,
            null
        ),
        Item(
            UUID.randomUUID().toString(),
            "Chinese Chair",
            R.color.button1,
            "Blue",
            80,
            R.drawable.images,
            1,
            StatusType.COMPLETED,
            null
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabBars()

    }

    private fun setUpTabBars() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = ViewPagerAdapter(this, items)

        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Active"
                1 -> "Completed"
                else -> null
            }
        }.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}