package com.example.homeworkstbc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.homeworkstbc.databinding.DetailsBinding

private const val NUMBER_PARAM = "idParam"
private const val STATUS_PARAM = "statusParam"

class Details : Fragment() {

    private var binding: DetailsBinding? = null
    private var paramNumber: Int? = null
    private var paramStatus: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramNumber = it.getInt(NUMBER_PARAM)
            paramStatus = it.getString(STATUS_PARAM)

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsBinding.inflate(inflater, container, false)


        setUp()
        handleGoBack()
        changeStatus()
        return binding?.root
    }


    companion object {

        fun newInstance(param1: Int,param2:String) =
            Details().apply {
                arguments = Bundle().apply {
                    putInt(NUMBER_PARAM, param1)
                    putString(STATUS_PARAM, param2)

                }
            }
    }

    private fun setUp ( ) {
        paramNumber.let {
            binding?.orderNumberDetails?.text = "Order #${it.toString()}"

        }
        paramStatus.let {
            if(paramStatus != "PENDING"){
                binding?.statusChangingButtons?.visibility = View.GONE
            } else {
                binding?.statusChangingButtons?.visibility = View.VISIBLE

            }
        }
    }


    private fun handleGoBack ( ) {
        binding?.goBack?.setOnClickListener {
            goBackToMainFragment()
        }
    }




    private fun changeStatus ( ) {

        binding?.toCancelled?.setOnClickListener {
            paramNumber.let {
                val resultBundle = Bundle().apply {
                    putString("statusChangedTo", "CANCELLED")
                    putInt("orderNumber", it!!)

                }

                parentFragmentManager.setFragmentResult("statusChangeRequest", resultBundle)

                goBackToMainFragment()
            }

        }
        binding?.toDelivered?.setOnClickListener {
            paramNumber.let {
                val resultBundle = Bundle().apply {
                    putString("statusChangedTo", "DELIVERED")
                    putInt("orderNumber", it!!)

                }

                parentFragmentManager.setFragmentResult("statusChangeRequest", resultBundle)

                goBackToMainFragment()
            }

        }

    }




    private fun goBackToMainFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main, MainFragment(), "mainFragment")
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}