package com.example.homeworkstbc

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.homeworkstbc.databinding.FragmentLoginBinding


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
                loginViewModel.login(email, password)
            }
        }
    }
    private fun navigateToHome() {

        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHome2())

    }

    private fun saveTokenToSharedPreferences(token: String) {
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", android.content.Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val expirationTime = System.currentTimeMillis() + 5 * 60 * 1000 // 5 minutes
        editor.putString("jwt_token", token)
        editor.putLong("jwt_expiration", expirationTime)
        editor.putString("email",binding.emailInputLogin.text.toString())

        editor.apply()
    }

    private fun observeLoginState() {
        loginViewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
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
                    binding.loginButton.isEnabled = true
                    binding.loginButtonLoader.visibility = View.GONE
                    binding.loginButton.setText(R.string.log_in)

                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
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