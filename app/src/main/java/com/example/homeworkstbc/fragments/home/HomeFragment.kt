// HomeFragment.kt
package com.example.homeworkstbc.fragments.home

import PostsAdapter
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.adapters.StoreAdapter
import com.example.homeworkstbc.databinding.FragmentHomeBinding
import com.example.homeworkstbc.fragments.BaseFragment
import com.example.homeworkstbc.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val storeAdapter by lazy { StoreAdapter() }
    private val postAdapter by lazy { PostsAdapter() }


    override fun start() {
        setupRecyclerView()

        collectStores()
        collectPosts()
    }

    private fun collectStores () {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.storesState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        storeAdapter.submitList(resource.data)
                        Log.d("home","${resource.data}")
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(),resource.message,Toast.LENGTH_LONG).show()
                    }
                    else -> {  }
                }
            }
        }
    }
    private fun collectPosts() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.postsState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        postAdapter.submitList(resource.data)
                        Log.d("HomeFragment", "Posts titles: ${resource.data}")
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                    }
                    else -> { }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.storesRecyclerView.apply {
            adapter = storeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        binding.postsRecyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

}
