package com.example.homeworkstbc

import ItemAdapter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Home : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val itemAdapter by lazy { ItemAdapter() }

    override fun start() {
        setupRecyclerView()
        observeViewModel()
        homeViewModel.fetchUsers()
        toProfile()
    }

    private fun setupRecyclerView() {
        binding.itemRecyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            homeViewModel.usersState.collectLatest { state ->
                when (state) {
                    is UsersState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.itemRecyclerView.visibility = View.GONE
                    }
                    is UsersState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.itemRecyclerView.visibility = View.VISIBLE
                        itemAdapter.submitList(state.users)
                    }
                    is UsersState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.itemRecyclerView.visibility = View.GONE
                        Toast.makeText(requireContext(),state.message,Toast.LENGTH_LONG).show()
                        println(state.message)
                    }
                }
            }
        }
    }

    private fun toProfile ( ) {
        binding.toProfile.setOnClickListener {
            findNavController().navigate(HomeDirections.actionHome2ToProfile())
        }
    }
}
