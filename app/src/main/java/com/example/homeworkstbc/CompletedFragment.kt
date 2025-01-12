package com.example.homeworkstbc

import FeedbackModal
import ItemAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.databinding.FragmentCompletedBinding

class CompletedFragment(private val items: List<Item>) : Fragment() {

    private var _binding: FragmentCompletedBinding? = null
    private val binding get() = _binding!!

    private val itemAdapter by lazy {
        ItemAdapter { item, position ->
            showFeedbackModal(item, position)
        }
    }
    private val mutableItems = items.toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachAdapter()
    }
    private fun showFeedbackModal(item: Item, position: Int) {
        FeedbackModal(item) { feedback ->
            addFeedback(feedback, item)
        }.show(parentFragmentManager, "FeedbackModal")
    }


    private fun addFeedback(feedback: String, item: Item) {
        val index = mutableItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            mutableItems[index] = mutableItems[index].copy(feedBack = feedback)
            val completedItems = mutableItems.filter { it.status == StatusType.COMPLETED }
            itemAdapter.submitList(completedItems.toList())
        }
    }



    private fun attachAdapter() {
        binding.completeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.completeRecyclerView.adapter = itemAdapter
        val activeItems = mutableItems.filter { it.status == StatusType.COMPLETED }
        itemAdapter.submitList(activeItems)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
