package com.example.homeworkstbc.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.databinding.FragmentHomeBinding
import com.example.homeworkstbc.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Home : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun start() {
        categoryAdapter = CategoryAdapter()
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryAdapter
        }
        setupSearch()
        observeState()
        observeSideEffects()
    }

    private fun setupSearch() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                homeViewModel.handleIntent(HomeIntent.SearchCategories(query))
            }
        })
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.state.collect { state ->
                Log.d("HomeFragment", "State received: isLoading=${state.isLoading}, Categories size=${state.categories.size}")
                binding.loading.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                categoryAdapter.submitList(state.categories)
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is HomeSideEffect.ShowError -> {
                        Toast.makeText(requireContext(), sideEffect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}