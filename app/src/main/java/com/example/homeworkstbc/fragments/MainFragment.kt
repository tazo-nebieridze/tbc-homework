package com.example.homeworkstbc.fragments

import ItemAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app.DataStoreManager
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.FragmentMainBinding
import com.example.homeworkstbc.viewModels.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var itemAdapter: ItemAdapter
    private var correctPasscode: String = "0934"

    override fun start() {
        getPasscode()
        setupRecyclerView()

        checkPasscode()
    }

    private fun getPasscode( ) {
        viewLifecycleOwner.lifecycleScope.launch {
            val stored = DataStoreManager.getPasscode().first()
            if (stored.isNullOrEmpty()) {
                DataStoreManager.savePasscode("0934")
            } else {
                correctPasscode = stored
            }
        }
    }

    private fun checkPasscode() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                mainViewModel.currentInput.collect { input ->
                    updatePasscodeDots(input)
                    if (input.length == 4) {
                        if (mainViewModel.verifyPasscode(correctPasscode)) {
                            Toast.makeText(requireContext(), "Passcode correct!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Wrong passcode. Try again.", Toast.LENGTH_SHORT).show()
                        }
                        mainViewModel.clearInput()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        itemAdapter = ItemAdapter(mainViewModel.lockScreenComponents) { key ->
            mainViewModel.onKeyPressed(key)
            if (key == "f") {
                Toast.makeText(requireContext(), "Fingerprint pressed", Toast.LENGTH_SHORT).show()
            }
        }
        binding.itemRecyclerView.apply {
            adapter = itemAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }


    private fun updatePasscodeDots(input: String) {
        val dotCount = input.length
        val dotsContainer = binding.passcodeDotsLayout
        dotsContainer.children.forEachIndexed { index, view ->
            val colorRes = if (index < dotCount) R.color.green else R.color.gray
            view.backgroundTintList = ContextCompat.getColorStateList(requireContext(), colorRes)
        }
    }
}








//curl -location request POST 'https://reqres.in/api/login"
//
//
//
//
//-header "Content-Type: application/json'
//
//--data-raw
//
//{
//    "email": "eve.holt@reqres.in",
//    "password": "cityslicka"
//}
//
//
//
//curl -location request POST 'https://reqres.in/api/register'
//
//header "Content-Type: application/json"
//--data-raw
//
//"email": "eve.holt@rdsdseqres.in",
//"password": "pistol"