package com.example.homeworkstbc.fragments

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.app.DataStoreManager
import com.example.homeworkstbc.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class Profile : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun start() {
        displayUserEmail()
        logOutClick()
        goBack()
    }

    private fun logOutClick ( ) {
        binding.logOut.setOnClickListener {
            logout()
        }
    }
    private fun displayUserEmail() {
        lifecycleScope.launch {
            val emailFlow = DataStoreManager.getEmail()
            emailFlow.collect { email ->
                binding.logInUser.text = email ?: "აბთუნა სიხარულიძე"
            }
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            DataStoreManager.clearData()
            findNavController().navigate(ProfileDirections.actionProfileToLoginFragment()) }
    }

    private fun goBack ( ) {
        binding.goBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }



}