// MainActivity.kt
package com.example.homeworkstbc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.app.DataStoreManager
import com.example.homeworkstbc.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nag_graph)

        lifecycleScope.launch {
            val token = dataStoreManager.getToken().firstOrNull()
            val expirationTime = dataStoreManager.getExpirationTime().firstOrNull()

            if (token != null && expirationTime != null && expirationTime > System.currentTimeMillis()) {
                navGraph.setStartDestination(R.id.home2)
            } else {
                navGraph.setStartDestination(R.id.loginFragment)
            }
            navController.graph = navGraph
        }
    }
}
