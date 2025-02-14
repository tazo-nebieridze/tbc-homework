// Home.kt (Fragment)
package com.example.homeworkstbc.fragments

import ItemAdapter
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.NetworkUtil
import com.example.homeworkstbc.databinding.FragmentHomeBinding
import com.example.homeworkstbc.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Home : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    @Inject
    lateinit var networkUtil: NetworkUtil
    private val homeViewModel: HomeViewModel by viewModels()
    private val itemAdapter by lazy { ItemAdapter() }

    override fun start() {
        setupRecyclerView()
        observePagingData()
        checkConnectivityAndAlert()
        toProfile()
    }

    private fun setupRecyclerView() {
        binding.itemRecyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewLifecycleOwner.lifecycleScope.launch {
            itemAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.visibility =
                    if (loadStates.refresh is LoadState.Loading) View.VISIBLE else View.GONE

                val errorState = loadStates.refresh as? LoadState.Error
                errorState?.let {
                    Toast.makeText(requireContext(), "An unexpected error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observePagingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.usersFlow.collectLatest { pagingData ->
                itemAdapter.submitData(pagingData)
            }
        }
    }

    private fun checkConnectivityAndAlert() {
        val isConnected = networkUtil.isNetworkAvailable()
        Toast.makeText(
            requireContext(),
            if (isConnected) "You are Online" else "You are Offline",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun toProfile() {
        binding.toProfile.setOnClickListener {
            findNavController().navigate(HomeDirections.actionHome2ToProfile())
        }
    }
}
