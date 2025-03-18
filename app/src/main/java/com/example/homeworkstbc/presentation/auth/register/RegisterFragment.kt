package com.example.homeworkstbc.presentation.auth.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.FragmentRegisterBinding
import com.example.homeworkstbc.presentation.base.BaseFragment
import com.example.homeworkstbc.presentation.utils.afterTextChangedDebounced
import com.example.homeworkstbc.presentation.utils.collectFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun start() {
        setupListeners()
        observeState()
        observeSideEffects()
    }

    private fun setupListeners() {
        binding.registerButton.setOnClickListener {
            registerViewModel.processIntent(RegisterIntent.RegisterClicked)
        }
        binding.toLoginPage.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        togglePasswordVisibility()
        toggleRepeatPasswordVisibility()
        binding.emailInputRegister.afterTextChangedDebounced(
            scope = viewLifecycleOwner.lifecycleScope,
            debounceMillis = 300L
        ) { text ->
            registerViewModel.processIntent(RegisterIntent.EmailChanged(text))
        }
        binding.passwordInputRegister.afterTextChangedDebounced(
            scope = viewLifecycleOwner.lifecycleScope,
            debounceMillis = 300L
        ) { text ->
            registerViewModel.processIntent(RegisterIntent.PasswordChanged(text))
        }
        binding.repeatPasswordInputRegister.afterTextChangedDebounced(
            scope = viewLifecycleOwner.lifecycleScope,
            debounceMillis = 300L
        ) { text ->
            registerViewModel.processIntent(RegisterIntent.RepeatPasswordChanged(text))
        }
    }

    private fun observeState() {
        viewLifecycleOwner.collectFlow(registerViewModel.state) { state ->
            binding.registerButton.isEnabled = state.isEmailValid && state.isPasswordValid && state.isRepeatPasswordValid
            if (state.isLoading) {
                binding.registerButton.text = "loaging ..."

            } else {
                binding.registerButton.text = getString(R.string.register)
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.collectFlow(registerViewModel.sideEffect) { effect ->
            when (effect) {
                is RegisterSideEffect.ShowError -> {
                    Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                }
                is RegisterSideEffect.NavigateToLogin -> {
                    parentFragmentManager.popBackStack()
                    parentFragmentManager.setFragmentResult(
                        "successRegister",
                        Bundle().apply {
                            putString("email", binding.emailInputRegister.text.toString())
                            putString("password", binding.passwordInputRegister.text.toString())
                        }
                    )
                }
            }
        }
    }

    private fun togglePasswordVisibility() {
        binding.passwordToggleRegister.setOnClickListener {
            val isPasswordVisible = binding.passwordInputRegister.inputType and
                    android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ==
                    android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.passwordInputRegister.inputType = if (isPasswordVisible) {
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
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
            binding.repeatPasswordInputRegister.inputType = if (isPasswordVisible) {
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            binding.repeatPasswordInputRegister.setSelection(binding.repeatPasswordInputRegister.text?.length ?: 0)
        }
    }
}