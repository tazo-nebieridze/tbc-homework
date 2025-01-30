package com.example.homeworkstbc.fragments


import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homeworkstbc.databinding.FragmentMainBinding
import com.example.homeworkstbc.viewModels.LoginViewModel
import com.example.homeworkstbc.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val mainViewModel: MainViewModel by viewModels()

    override fun start() {
        readUser()
        saveUser()
    }
    private fun saveUser ( ) {
        binding.saveButton.setOnClickListener {
            val firstName = binding.firstNameInput.text.toString()
            val lastName = binding.lastNameInput.text.toString()
            val email = binding.emailInput.text.toString()

            mainViewModel.saveUser(firstName, lastName, email)
        }
    }

    private fun readUser ( ) {
        binding.readButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val prefs = mainViewModel.userPrefsFlow.first()
                    binding.userDetailsText.text = "First Name: ${prefs.firstName}\nLast Name: ${prefs.lastName}\nEmail: ${prefs.email}"
                }
                }
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