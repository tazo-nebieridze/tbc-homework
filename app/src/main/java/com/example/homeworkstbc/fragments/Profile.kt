package com.example.homeworkstbc.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.app.DataStoreManager
import com.example.homeworkstbc.databinding.FragmentProfileBinding
import com.example.homeworkstbc.viewModels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Profile : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {


    private val profileViewModel: ProfileViewModel by viewModels()


    override fun start() {
        displayUserEmail()
        logOutClick()
        goBack()
    }


    private fun displayUserEmail() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                profileViewModel.emailFlow.collect { email ->
                    binding.logInUser.text = email ?: "აბთუნა სიხარულიძე"
                }
            }
        }
    }

    private fun logOutClick() {
        binding.logOut.setOnClickListener {
            profileViewModel.logout()
            findNavController().navigate(ProfileDirections.actionProfileToLoginFragment())
        }
    }

    private fun goBack ( ) {
        binding.goBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }



}