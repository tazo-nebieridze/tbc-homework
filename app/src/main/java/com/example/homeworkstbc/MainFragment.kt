package com.example.homeworkstbc

import ItemAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val fragmentViewModel: FragmentViewModel by viewModels()

    private val itemAdapter by lazy {
        ItemAdapter ()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
    }

    private fun setUp ( ) {
        binding.itemRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
        }

        fragmentViewModel.filteredChatList.observe(viewLifecycleOwner) { chatList ->
            itemAdapter.submitList(chatList)
        }

        binding.searchButton.setOnClickListener {
            val query = binding.searchField.text.toString()
            fragmentViewModel.filterChats(query)
        }
    }
}
