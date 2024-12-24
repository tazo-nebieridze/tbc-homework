package com.example.homeworkstbc

import CategoryAdapter
import ItemAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val categories = listOf(
        Category(
            "Party",

            listOf(
                Item(1, "Party Item 1", "$20.00", R.drawable.apple_logo),
                Item(2, "Party Item 2", "$21.00", R.drawable.apple_logo),
                Item(3, "Party Item 3", "$22.00", R.drawable.apple_logo),
                Item(4, "Party Item 4", "$23.00", R.drawable.apple_logo),
                Item(5, "Party Item 5", "$24.00", R.drawable.apple_logo),
                Item(6, "Party Item 6", "$25.00", R.drawable.apple_logo)
            ),
            icon = R.drawable.baseline_arrow_back_24
        ),
        Category(
            "Camping",
            listOf(
                Item(7, "Camping Item 1", "$30.00", R.drawable.facebook_logo),
                Item(8, "Camping Item 2", "$31.00", R.drawable.facebook_logo),
                Item(9, "Camping Item 3", "$32.00", R.drawable.facebook_logo),
                Item(10, "Camping Item 4", "$33.00", R.drawable.facebook_logo),
                Item(11, "Camping Item 5", "$34.00", R.drawable.facebook_logo),
                Item(12, "Camping Item 6", "$35.00", R.drawable.facebook_logo)
            ),
            icon = R.drawable.baseline_arrow_back_24
        ),
        Category(
            "Camping1",

            listOf(
                Item(13, "Camping1 Item 1", "$40.00", R.drawable.google_logo),
                Item(14, "Camping1 Item 2", "$41.00", R.drawable.google_logo),
                Item(15, "Camping1 Item 3", "$42.00", R.drawable.google_logo),
                Item(16, "Camping1 Item 4", "$43.00", R.drawable.google_logo),
                Item(17, "Camping1 Item 5", "$44.00", R.drawable.google_logo),
                Item(18, "Camping1 Item 6", "$45.00", R.drawable.google_logo)
            ),
            icon = R.drawable.baseline_arrow_back_24

            ),
        Category(
            "Camping2",
            listOf(
                Item(19, "Camping2 Item 1", "$50.00", R.drawable.facebook_logo),
                Item(20, "Camping2 Item 2", "$51.00", R.drawable.facebook_logo),
                Item(21, "Camping2 Item 3", "$52.00", R.drawable.facebook_logo),
                Item(22, "Camping2 Item 4", "$53.00", R.drawable.facebook_logo),
                Item(23, "Camping2 Item 5", "$54.00", R.drawable.facebook_logo),
                Item(24, "Camping2 Item 6", "$55.00", R.drawable.facebook_logo)
            ),
            icon = R.drawable.baseline_arrow_back_24
            ),
        Category(
            "Camping3",

            listOf(
                Item(21, "Camping3 Item 1", "$50.00", R.drawable.google_logo),
                Item(22, "Camping3 Item 2", "$51.00", R.drawable.google_logo),
                Item(23, "Camping3 Item 3", "$52.00", R.drawable.google_logo),
                Item(24, "Camping3 Item 4", "$53.00", R.drawable.google_logo),
                Item(25, "Camping3 Item 5", "$54.00", R.drawable.google_logo),
                Item(26, "Camping3 Item 6", "$55.00", R.drawable.google_logo)
            ),
            icon = R.drawable.baseline_arrow_back_24
        )
    )


    private val allItems = categories.flatMap { it.items }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val allCategories = listOf(Category("All", allItems,R.drawable.baseline_arrow_back_24)) + categories

        println(allCategories)

        attachCategories(allCategories)

        attachItemsContainer()

    }


    private fun attachCategories ( allCategories: List<Category>) {
        binding.categoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = CategoryAdapter(allCategories) { category ->
            updateItems(category)
        }
    }

    private fun attachItemsContainer ( ) {
        binding.itemRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.itemRecyclerView.adapter = ItemAdapter(allItems)
    }

    private fun updateItems(category: Category) {
        binding.itemRecyclerView.adapter = ItemAdapter(category.items)
    }



    data class Item(
        val id: Int,
        val name: String,
        val price: String,
        val image: Int
    )

    data class Category(
        val name: String,
        val items: List<Item>,
        val icon: Int
    )
}
