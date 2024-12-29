package com.example.homeworkstbc
import ItemAdapter
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.databinding.FragmentMainFragmentBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainFragment : Fragment() {

    private var binding: FragmentMainFragmentBinding? = null

    private var param1: String? = null
    private var param2: String? = null

    private var selectedCategory : String = "PENDING"

    private val itemAdapter by lazy {
        ItemAdapter( ) {
                number , status ->

            attachDetailsFormFragment(number,status)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainFragmentBinding.inflate(inflater, container, false)



        attachItemsContainer()
        handleCategoryChange()
        waitForAddingNewLocation()
        return binding?.root
    }




    companion object {

        fun newInstance(param1: String, param2 : String ) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



    private fun handleCategoryChange() {
        val categories = mapOf(
            "PENDING" to binding?.pendingCategory,
            "DELIVERED" to binding?.deliveredCategory,
            "CANCELLED" to binding?.cancelledCategory
        )

        categories.forEach { (category, view) ->
            view?.setOnClickListener {
                if (selectedCategory != category) {
                    view.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    view.setBackgroundResource(R.drawable.add_new_button)

                    categories[selectedCategory]?.let { previousView ->
                        previousView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                        previousView.background = null
                    }

                    selectedCategory = category
                    itemAdapter.submitList((activity as MainActivity).orders.filter { it.status == selectedCategory })

                }
            }
        }
    }



    private fun attachItemsContainer() {
        binding?.itemRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.itemRecyclerView?.adapter = itemAdapter
        itemAdapter.submitList((activity as MainActivity).orders.filter { it.status == selectedCategory })
    }


    private fun attachDetailsFormFragment( number : Int, status : String) {

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(
            R.id.main,
            Details.newInstance(number,status),
            "DetailsFragment")
        transaction.commit()
    }




    private fun waitForAddingNewLocation() {
        parentFragmentManager.setFragmentResultListener(
            "statusChangeRequest", viewLifecycleOwner
        ) { _, bundle ->
            val newStatus = bundle.getString("statusChangedTo")
            val selectedOrderNumber = bundle.getInt("orderNumber")

            val updatedOrders = (activity as MainActivity).orders

            val orderIndex = updatedOrders.indexOfFirst { it.orderCount == selectedOrderNumber }
            if (orderIndex != -1) {
                updatedOrders[orderIndex].status = newStatus!!

                (activity as MainActivity).orders = updatedOrders
            }

            itemAdapter.submitList(updatedOrders.filter { it.status == selectedCategory })
        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
