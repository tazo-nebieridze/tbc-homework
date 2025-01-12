package com.example.homeworkstbc

import ItemAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.databinding.FragmentActiveBinding

class ActiveFragment(private val items: List<Item>) : Fragment() {

    private var _binding: FragmentActiveBinding? = null
    private val binding get() = _binding!!

    private val itemAdapter by lazy {
        ItemAdapter { item, position ->
            showFeedbackModal(item, position)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachAdapter()
    }
    private fun showFeedbackModal(item: Item, position: Int) {

    }
    private fun attachAdapter ( ) {
        binding.activeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.activeRecyclerView.adapter = itemAdapter
        val activeItems = items.filter { it.status == StatusType.ACTIVE }
        itemAdapter.submitList(activeItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
