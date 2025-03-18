package com.example.homeworkstbc.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.homeworkstbc.R
import com.example.homeworkstbc.domain.utils.PreferenceKeys
import com.example.homeworkstbc.databinding.ActivityMainBinding
import com.example.homeworkstbc.domain.useCase.CheckTokenValidityUseCase
import com.example.homeworkstbc.domain.useCase.ClearValueUseCase

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var checkTokenValidityUseCase: CheckTokenValidityUseCase

    @Inject
    lateinit var clearValueUseCase: ClearValueUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigation()
    }

    private fun setNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        lifecycleScope.launch {
            val isTokenValid = checkTokenValidityUseCase()
            val graph = navController.navInflater.inflate(R.navigation.nag_graph)
            graph.setStartDestination(if (isTokenValid) R.id.home2 else R.id.loginFragment)
            navController.setGraph(graph, null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch {
            clearValueUseCase(PreferenceKeys.TOKEN)
            clearValueUseCase(PreferenceKeys.TOKEN_VALIDITY_TIME)
        }
    }
}