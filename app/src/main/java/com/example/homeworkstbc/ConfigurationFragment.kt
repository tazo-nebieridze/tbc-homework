package com.example.homeworkstbc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homeworkstbc.databinding.FragmentConfigurationBinding

class ConfigurationFragment : Fragment() {

    private var _binding: FragmentConfigurationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startGame()
    }

    private fun startGame ( ) {
        binding.startGameButton.setOnClickListener {
            val gridSize = binding.sizeInput.text.toString().toInt()
            if (gridSize == 3 || gridSize == 4) {
                val fragment = GameFragment.newInstance(gridSize)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main, fragment,"game")
                    .addToBackStack("game")
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Input only 3 or 4", Toast.LENGTH_SHORT).show()
                binding.sizeInput.text?.clear()
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
