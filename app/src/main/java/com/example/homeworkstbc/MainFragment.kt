package com.example.homeworkstbc
import ItemAdapter
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.databinding.FragmentMainFragmentBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainFragment : Fragment() {

    private var binding: FragmentMainFragmentBinding? = null

    private var param1: String? = null
    private var param2: String? = null


    private val itemAdapter by lazy {
        ItemAdapter { date ->
            formatMessageDate(date)
        }
    }

    private var messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainFragmentBinding.inflate(inflater, container, false)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        handleNewMessage()
        attachItemsContainer()

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



    private fun handleNewMessage ( ) {
        binding?.messageButton?.setOnClickListener {
            if(binding?.editText?.text!!.isEmpty()){
                Toast.makeText(requireContext(),"can not add empty message", Toast.LENGTH_SHORT).show()
            } else {
                val newMessageList = messages
                newMessageList.add(
                    Message(
                        id = UUID.randomUUID().toString(),
                        text = binding?.editText?.text.toString(),
                        date = Date(),
                        sender = if (messages.size % 2 == 0 ) SenderType.OTHER else SenderType.CURRENT
                    )
                )
                messages = newMessageList

                itemAdapter.submitList(newMessageList)

                binding?.editText?.text!!.clear()

                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding?.editText?.windowToken, 0)
            }
        }
    }


    private fun attachItemsContainer() {
        binding?.itemRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.itemRecyclerView?.adapter = itemAdapter
        itemAdapter.submitList(messages)
    }


  private  fun formatMessageDate(date: Date): String {
        val today = Date()
        val dateFormat = if (isSameDay(date, today)) {
            SimpleDateFormat("'Today,' h:mm a", Locale.getDefault())
        } else {
            SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
        }
        return dateFormat.format(date)
    }

   private fun isSameDay(date1: Date, date2: Date): Boolean {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return sdf.format(date1) == sdf.format(date2)
    }




    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
