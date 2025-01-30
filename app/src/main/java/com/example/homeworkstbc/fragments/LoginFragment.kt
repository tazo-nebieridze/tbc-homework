package com.example.homeworkstbc.fragments

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.app.DataStoreManager
import com.example.homeworkstbc.viewModels.LoginState
import com.example.homeworkstbc.viewModels.LoginViewModel
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.FragmentLoginBinding
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()



    override fun start() {
        observeLoginState()
        onLogin()
        togglePasswordVisibility()
        navigateToRegister()
        listenSuccessRegister()
//        listenToState1()
    }

    private fun listenToState1 ( ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState1.consume {
                    Log.d("loginState",this.toString())
                }
            }
        }
    }


    private fun onLogin() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailInputLogin.text.toString()
            val password = binding.passwordInputLogin.text.toString()
            val rememberMe = binding.rememberMe.isChecked

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_LONG).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Input a valid email address", Toast.LENGTH_LONG).show()
            } else {
                loginViewModel.login(email, password)
            }
        }
    }
    private fun navigateToHome() {

        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHome2())

    }

    private fun saveTokenToSharedPreferences(token: String) {
        val expirationTime = System.currentTimeMillis() + 50 * 60 * 1000
        val email = binding.emailInputLogin.text.toString()

        loginViewModel.saveSession(token,email,expirationTime)
    }


    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Idle -> {
                            binding.loginButton.isEnabled = true
                            binding.loginButtonLoader.visibility = View.GONE
                            binding.loginButton.setText(R.string.log_in)
                        }
                        is LoginState.Loading -> {
                            binding.loginButton.isEnabled = false
                            binding.loginButton.text = ""
                            binding.loginButtonLoader.visibility = View.VISIBLE
                        }
                        is LoginState.Success -> {
                            binding.loginButton.isEnabled = true
                            binding.loginButtonLoader.visibility = View.GONE
                            binding.loginButton.setText(R.string.log_in)

                            val token = state.data?.token

                            if (token != null && binding.rememberMe.isChecked) {
                                saveTokenToSharedPreferences(token)
                            }
                            navigateToHome()
                        }
                        is LoginState.Error -> {
                            Log.d("loginState","error")
                            binding.loginButton.isEnabled = true
                            binding.loginButtonLoader.visibility = View.GONE
                            binding.loginButton.setText(R.string.log_in)

                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
    private fun togglePasswordVisibility() {
        binding.passwordToggleLogin.setOnClickListener {
            val isPasswordVisible = binding.passwordInputLogin.inputType and
                    android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ==
                    android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            if (isPasswordVisible) {
                binding.passwordInputLogin.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD

            } else {
                binding.passwordInputLogin.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            }

            binding.passwordInputLogin.setSelection(binding.passwordInputLogin.text?.length ?: 0)
        }
    }


    private fun listenSuccessRegister ( ) {
        parentFragmentManager.setFragmentResultListener("successRegister", this) { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.emailInputLogin.setText(email)
            binding.passwordInputLogin.setText(password)

        }
    }


    private fun navigateToRegister ( ) {
        binding.toRegisterPage.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

}