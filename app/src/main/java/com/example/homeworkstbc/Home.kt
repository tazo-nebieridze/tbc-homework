package com.example.homeworkstbc

import ItemAdapter
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Home : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val itemAdapter by lazy { ItemAdapter() }



    override fun start() {
        homeViewModel.fetchUsers()
        setupRecyclerView()
        observeViewModel()
        toProfile()
    }

    private fun setupRecyclerView() {
        binding.itemRecyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ddddd","destroyed")

    }
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG)
                                .show()
                            println(state.message)
                        }
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
