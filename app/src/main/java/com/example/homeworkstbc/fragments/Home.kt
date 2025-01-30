package com.example.homeworkstbc.fragments

import ItemAdapter
import User
import android.util.Log

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.viewModels.HomeViewModel
import com.example.homeworkstbc.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Home : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val itemAdapter by lazy { ItemAdapter() }

    override fun start() {
        setupRecyclerView()
        observePagingData()
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

//                retry.isVisible = loadState.refresh !is LoadState.Loading
                val errorState = loadStates.refresh as? LoadState.Error
                errorState?.let {
                    val errorMessage = "An unexpected error occurred"
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observePagingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.usersFlow.collectLatest { pagingData: PagingData<User> ->
                Log.d("homeFragment","observe")
                itemAdapter.submitData(pagingData)
            }
        }
    }


    private fun toProfile ( ) {
        binding.toProfile.setOnClickListener {
            findNavController().navigate(HomeDirections.actionHome2ToProfile())
        }
    }
}
