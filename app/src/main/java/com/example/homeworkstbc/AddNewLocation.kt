package com.example.homeworkstbc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.homeworkstbc.databinding.FragmentAddNewLocationBinding


class AddNewLocation : Fragment() {

    private var binding: FragmentAddNewLocationBinding? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewLocationBinding.inflate(inflater, container, false)


        if((activity as MainActivity).isEdit){
           binding?.addNewLocation?.text = "Edit Location"
            binding?.deleteLocation?.visibility = View.VISIBLE
        } else {
            binding?.deleteLocation?.visibility = View.GONE

        }

        binding?.addNewLocation?.setOnClickListener {

            if((activity as MainActivity).isEdit){
                editLocation()
            } else {
                addNewLocation()

            }
        }

        handleGoBack()
        deleteLocationButtonClick()
        return binding?.root
    }

    private fun deleteLocationButtonClick ( ) {
        binding?.deleteLocation?.setOnClickListener{
            deleteLocation((activity as MainActivity).locationId)
            (activity as MainActivity).isEdit = false
            (activity as MainActivity).locationId = -1
            goBackToMainFragment()
            true

        }
    }

    private fun deleteLocation ( id:Int ) {
        val locationToRemove = (activity as MainActivity).locations.find { it.id == id }

        if (locationToRemove != null) {
            (activity as MainActivity).locations.remove(locationToRemove)
        }
    }

    private fun addNewLocation ( ) {
            val locationName = binding?.locationNameInput?.text.toString()
            val location = binding?.locationInput?.text.toString()

            if (locationName.isEmpty() || location.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all inputs", Toast.LENGTH_SHORT).show()
            } else {
                val resultBundle = Bundle().apply {
                    putString("locationName", locationName)
                    putString("location", location)
                }
                parentFragmentManager.setFragmentResult("newLocationRequest", resultBundle)

                goBackToMainFragment()
            }
    }

    private fun editLocation ( ) {
        val locationName = binding?.locationNameInput?.text.toString()
        val location = binding?.locationInput?.text.toString()

        if (locationName.isEmpty() || location.isEmpty()) {
            Toast.makeText(requireContext(), "Fill all inputs", Toast.LENGTH_SHORT).show()
        } else {
            val resultBundle = Bundle().apply {
                putString("locationName", locationName)
                putString("location", location)
            }
            parentFragmentManager.setFragmentResult("editLocationRequest", resultBundle)

            goBackToMainFragment()
        }
    }

    private fun handleGoBack ( ) {
        binding?.goBackToLocations?.setOnClickListener{
            goBackToMainFragment()
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
    //    private fun getLocationId ( ) {
//            locationId = arguments?.getInt("id")
//        println(locationId)
//    }
//
//    private fun getIsEdit ( ) {
//            isEdit = arguments?.getString("isEdit")
//
//        println(isEdit)
//
//        if(isEdit == "edit") {
//            binding?.addNewLocation?.text = "Edit Location"
//        }
//    }


}