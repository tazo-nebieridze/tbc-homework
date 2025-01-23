package com.example.homeworkstbc

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.homeworkstbc.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun start() {
        observeRegisterState()
        onRegister()
        togglePasswordVisibility()
        toggleRepeatPasswordVisibility()
        toLogin()
    }

    private fun onRegister() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailInputRegister.text.toString()
            val password = binding.passwordInputRegister.text.toString()
            val repeatPassword = binding.repeatPasswordInputRegister.text.toString()
            if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_LONG).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Input a valid email address", Toast.LENGTH_LONG).show()
            } else if (repeatPassword != password) {
                Toast.makeText(requireContext(), "please repeat correct password", Toast.LENGTH_LONG).show()
            } else {
                registerViewModel.register(email, password)
            }
        }
    }

    private fun observeRegisterState() {
        registerViewModel.registerState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegisterState.Loading -> {
                    binding.registerButton.isEnabled = false
                    binding.registerButton.text = ""
                    binding.registerButtonLoader.visibility = View.VISIBLE
                }
                is RegisterState.Success -> {
                    binding.registerButton.isEnabled = true
                    binding.registerButtonLoader.visibility = View.GONE
                    binding.registerButton.setText(R.string.register)

                    parentFragmentManager.popBackStack()

                    parentFragmentManager.setFragmentResult(
                        "successRegister",
                        Bundle().apply {
                            putString("email", binding.emailInputRegister.text.toString())
                            putString("password", binding.passwordInputRegister.text.toString())
                        }
                    )

                }
                is RegisterState.Error -> {
                    binding.registerButton.isEnabled = true
                    binding.registerButtonLoader.visibility = View.GONE
                    binding.registerButton.setText(R.string.register)

                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun togglePasswordVisibility() {
        binding.passwordToggleRegister.setOnClickListener {
            val isPasswordVisible = binding.passwordInputRegister.inputType and
                    android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ==
                    android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            if (isPasswordVisible) {
                binding.passwordInputRegister.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                binding.passwordInputRegister.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }

            binding.passwordInputRegister.setSelection(binding.passwordInputRegister.text?.length ?: 0)
        }
    }

    private fun toggleRepeatPasswordVisibility() {
        binding.repeatPasswordToggleRegister.setOnClickListener {
            val isPasswordVisible = binding.repeatPasswordInputRegister.inputType and
                    android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ==
                    android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            if (isPasswordVisible) {
                binding.repeatPasswordInputRegister.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                binding.repeatPasswordInputRegister.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }

            binding.repeatPasswordInputRegister.setSelection(binding.repeatPasswordInputRegister.text?.length ?: 0)
        }
    }


    private fun toLogin ( ) {
        binding.toLoginPage.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }
}
