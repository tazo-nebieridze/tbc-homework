// MainActivity.kt
package com.example.homeworkstbc

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.app.DataStoreManager
import com.example.homeworkstbc.databinding.ActivityMainBinding
import com.example.homeworkstbc.fragments.FavoritesFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var dataStoreManager: DataStoreManager
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()

        binding.ivHeart.setOnClickListener {
            navController.navigate(R.id.favoritesFragment)
            highlightSelectedTab(Tab.FAVORITES)
        }

        binding.ivHome.setOnClickListener {
            navController.navigate(R.id.mainFragment)
            highlightSelectedTab(Tab.HOME)
        }

        binding.ivChat.setOnClickListener {
            navController.navigate(R.id.chatFragment)
            highlightSelectedTab(Tab.CHAT)
        }

        highlightSelectedTab(Tab.HOME)

    }
    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


    }

    private fun highlightSelectedTab(selected: Tab) {
        binding.ivHeart.background = null
        binding.ivHeart.setColorFilter(
            ContextCompat.getColor(this, R.color.gray),
            PorterDuff.Mode.SRC_IN
        )
        binding.ivHome.background = null
        binding.ivHome.setColorFilter(
            ContextCompat.getColor(this, R.color.gray),
            PorterDuff.Mode.SRC_IN
        )
        binding.ivChat.background = null
        binding.ivChat.setColorFilter(
            ContextCompat.getColor(this, R.color.gray),
            PorterDuff.Mode.SRC_IN
        )

        when (selected) {
            Tab.FAVORITES -> {
                binding.ivHeart.background = ContextCompat.getDrawable(this, R.drawable.bg_green_circle)
                binding.ivHeart.setColorFilter(
                    ContextCompat.getColor(this, android.R.color.white),
                    PorterDuff.Mode.SRC_IN
                )
            }
            Tab.HOME -> {
                binding.ivHome.background = ContextCompat.getDrawable(this, R.drawable.bg_green_circle)
                binding.ivHome.setColorFilter(
                    ContextCompat.getColor(this, android.R.color.white),
                    PorterDuff.Mode.SRC_IN
                )
            }
            Tab.CHAT -> {
                binding.ivChat.background = ContextCompat.getDrawable(this, R.drawable.bg_green_circle)
                binding.ivChat.setColorFilter(
                    ContextCompat.getColor(this, android.R.color.white),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }
    }

    enum class Tab { FAVORITES, HOME, CHAT }
}