package com.example.homeworkstbc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeworkstbc.databinding.FragmentHomeBinding


class Home : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    override fun start() {
        displayUserEmail()
        logOutClick()
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
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", android.content.Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("jwt_token")
        editor.remove("jwt_expiration")
        editor.remove("email")
        editor.apply()

        findNavController().navigate(R.id.action_home2_to_loginFragment)
    }




}