package com.example.homeworkstbc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.homeworkstbc.databinding.FragmentLoginBinding
import com.example.homeworkstbc.databinding.FragmentMainBinding


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoginState()
        onLogin()
        togglePasswordVisibility()
    }


    private fun onLogin() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailInputLogin.text.toString()
            val password = binding.passwordInputLogin.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_LONG).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Input a valid email address", Toast.LENGTH_LONG).show()
            } else {
                loginViewModel.login(email, password)
            }
        }
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
                    Toast.makeText(
                        requireContext(),
                        "Login successful",
                        Toast.LENGTH_LONG
                    ).show()

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


}