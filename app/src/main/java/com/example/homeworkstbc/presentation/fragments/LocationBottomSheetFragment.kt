// LocationBottomSheetDialogFragment.kt
package com.example.homeworkstbc.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.FragmentLocationBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentLocationBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Retrieve arguments from the bundle
        val lat = arguments?.getDouble("lat") ?: 0.0
        val lng = arguments?.getDouble("lng") ?: 0.0
        val title = arguments?.getString("title")
        val address = arguments?.getString("address")

        binding.tvLat.text = "Latitude: $lat"
        binding.tvLng.text = "Longitude: $lng"

        if (!title.isNullOrEmpty() && !address.isNullOrEmpty()) {
            binding.tvTitle.text = title
            binding.tvAddress.text = address
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvAddress.visibility = View.VISIBLE
        } else {
            binding.tvTitle.visibility = View.GONE
            binding.tvAddress.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
