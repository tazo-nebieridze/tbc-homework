package com.example.homeworkstbc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", android.content.Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "აბთუნა სიხარულიძე")

        binding.logInUser.text = email
    }

    private fun logout() {
        lifecycleScope.launch {
            DataStoreManager.clearData(requireContext())
            findNavController().navigate(ProfileDirections.actionProfileToLoginFragment()) }
    }

    private fun goBack ( ) {
        binding.goBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }



}