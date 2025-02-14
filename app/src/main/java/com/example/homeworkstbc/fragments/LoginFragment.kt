// LoginFragment.kt
package com.example.homeworkstbc.fragments

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.FragmentLoginBinding
import com.example.homeworkstbc.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun start() {
        observeLoginState()
        onLogin()
        togglePasswordVisibility()
        navigateToRegister()
        listenSuccessRegister()
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
                loginViewModel.login(email, password, rememberMe)
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHome2())
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collect { state ->
                    when (state) {
                        is Resource.Idle -> {
                            binding.loginButton.isEnabled = true
                            binding.loginButtonLoader.visibility = View.GONE
                            binding.loginButton.setText(R.string.log_in)
                        }
                        is Resource.Loading -> {
                            binding.loginButton.isEnabled = false
                            binding.loginButton.text = ""
                            binding.loginButtonLoader.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.loginButton.isEnabled = true
                            binding.loginButtonLoader.visibility = View.GONE
                            binding.loginButton.setText(R.string.log_in)
                            navigateToHome()
                        }
                        is Resource.Error -> {
                            binding.loginButton.isEnabled = true
                            binding.loginButtonLoader.visibility = View.GONE
                            binding.loginButton.setText(R.string.log_in)
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                        }
                        else ->{

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

            binding.passwordInputLogin.inputType = if (isPasswordVisible) {
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }

            binding.passwordInputLogin.setSelection(binding.passwordInputLogin.text?.length ?: 0)
        }
    }

    private fun listenSuccessRegister() {
        parentFragmentManager.setFragmentResultListener("successRegister", this) { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.emailInputLogin.setText(email)
            binding.passwordInputLogin.setText(password)
        }
    }

    private fun navigateToRegister() {
        binding.toRegisterPage.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }
}
