package com.example.homeworkstbc

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.homeworkstbc.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRegisterState()
        onRegister()
        togglePasswordVisibility()
    }

    private fun onRegister() {
        binding.registerButton.setOnClickListener {
            val email = binding.emailInputRegister.text.toString()
            val password = binding.passwordInputRegister.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_LONG).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Input a valid email address", Toast.LENGTH_LONG).show()
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
                    Toast.makeText(
                        requireContext(),
                        "Registration successful",
                        Toast.LENGTH_LONG
                    ).show()

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
}
