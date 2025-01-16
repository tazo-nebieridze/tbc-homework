package com.example.homeworkstbc

import CardViewModel
import InputAdapter
import ItemAdapter
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.homeworkstbc.databinding.FragmentMainBinding
import com.example.homeworkstbc.databinding.InputRecyclerBinding
import com.example.homeworkstbc.databinding.RecyclerItemBinding
import kotlinx.serialization.json.Json

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val cardViewModel: CardViewModel by viewModels()


    private val itemAdapter by lazy {
        ItemAdapter(cardViewModel.inputsData)  {
                binding, item ->
            attachInputsRecycler(binding,item)

        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputsList = cardViewModel.inputsData

        inputsList.forEachIndexed { index, list ->
            Log.d("MainFragment", "Group $index:")
            list.forEach { input ->
                Log.d("MainFragment", "Input: $input")
            }
        }
        attachAdapter()
        handleSubmit()
    }

    private  fun attachAdapter ( ) {
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.itemRecyclerView.adapter = itemAdapter
    }

    private fun handleSubmit ( ) {
        binding.submitButton.setOnClickListener {
            val missingFields = mutableListOf<String>()

            cardViewModel.inputsData.forEach { group ->
                group.forEach { input ->
                    val value = cardViewModel.fieldValues[input.fieldId]

                    if (input.required && (value.isNullOrEmpty())) {
                        missingFields.add(input.hint.toString())
                    }
                }
            }

            if (missingFields.isNotEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill required fields: ${missingFields.joinToString(", ")}",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Log.d("MainFragment", "${cardViewModel.fieldValues}")
            }
        }
    }

    private fun saveValues (key:Int, value:String ) {

        cardViewModel.fieldValues[key] = value
    }

    private fun attachInputsRecycler(binding: RecyclerItemBinding, item: List<Input>) {
        binding.inputRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        val innerAdapter = InputAdapter() {
            key, value ->
            saveValues(key,value)
        }
        binding.inputRecyclerView.adapter = innerAdapter

        innerAdapter.submitList(item)
    }

}