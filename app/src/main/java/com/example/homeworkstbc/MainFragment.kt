package com.example.homeworkstbc
import ItemAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkstbc.databinding.FragmentMainFragmentBinding


class MainFragment : Fragment() {

    private var binding: FragmentMainFragmentBinding? = null

    private val itemAdapter by lazy {
        ItemAdapter( ) { id ->
            attachLocationFormFragmentEdit(id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainFragmentBinding.inflate(inflater, container, false)

        waitForAddingNewLocation()

        waitForEditLocation()



        attachItemsContainer()

        binding?.addNewLocation?.setOnClickListener {
            attachLocationFormFragment()
        }

        return binding?.root
    }




    private fun waitForAddingNewLocation ( ) {
        parentFragmentManager.setFragmentResultListener(
            "newLocationRequest", viewLifecycleOwner
        ) { _, bundle ->
            val name = bundle.getString("locationName")
            val location = bundle.getString("location")
            if (name != null && location != null) {
                val newLocation = MainActivity.Location(
                    id = ((activity as MainActivity).locations.size ?: 0) + 1,
                    name = name,
                    location = location,
                    icon = R.drawable.outline_home_24
                )
                addLocation(newLocation)
            }
        }
    }

    private fun waitForEditLocation ( ) {
        parentFragmentManager.setFragmentResultListener(
            "editLocationRequest", viewLifecycleOwner
        ) { _, bundle ->
            val name = bundle.getString("locationName")
            val location = bundle.getString("location")
            if (name != null && location != null) {

                (activity as MainActivity).locations.forEach { singleLocation ->

                    if(singleLocation.id == (activity as MainActivity).locationId){
                        singleLocation.name = name
                        singleLocation.location = location
                        (activity as MainActivity).isEdit = false
                        (activity as MainActivity).locationId = -1
                    }
                }

            }
        }
    }

    private fun addLocation(location: MainActivity.Location) {

        (activity as MainActivity).locations.add(0,location)


    }

    private fun attachItemsContainer() {
        binding?.itemRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.itemRecyclerView?.adapter = itemAdapter
        itemAdapter.submitList((activity as MainActivity).locations)
    }

    private fun attachLocationFormFragment() {

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main, AddNewLocation(), "locationFormFragment")
        transaction.commit()
    }

    private fun attachLocationFormFragmentEdit ( id : Int ) {

        (activity as MainActivity).isEdit = true
        (activity as MainActivity).locationId = id

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main, AddNewLocation(), "locationFormFragment")
        transaction.commit()

    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
