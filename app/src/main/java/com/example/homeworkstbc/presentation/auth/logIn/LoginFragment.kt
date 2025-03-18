package com.example.homeworkstbc.presentation.auth.logIn

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homeworkstbc.R
import com.example.homeworkstbc.databinding.FragmentLoginBinding
import com.example.homeworkstbc.presentation.base.BaseFragment
import com.example.homeworkstbc.presentation.utils.afterTextChangedDebounced
import com.example.homeworkstbc.presentation.utils.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun start() {
        setupListeners()
        observeState()
        observeSideEffects()
        listenSuccessRegister()
    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            loginViewModel.processIntent(LoginIntent.LoginClicked)
        }
        binding.toRegisterPage.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        togglePasswordVisibility()
        binding.rememberMe.setOnCheckedChangeListener { _, isChecked ->
            loginViewModel.processIntent(LoginIntent.RememberMeToggled(isChecked))
        }
        binding.emailInputLogin.afterTextChangedDebounced(
            scope = viewLifecycleOwner.lifecycleScope,
            debounceMillis = 300L
        ) { text ->
            loginViewModel.processIntent(LoginIntent.EmailChanged(text))
        }
        binding.passwordInputLogin.afterTextChangedDebounced(
            scope = viewLifecycleOwner.lifecycleScope,
            debounceMillis = 300L
        ) { text ->
            loginViewModel.processIntent(LoginIntent.PasswordChanged(text))
        }
    }

    private fun observeState() {
        viewLifecycleOwner.collectFlow(loginViewModel.state) { state ->
            binding.loginButton.isEnabled = state.isEmailValid && state.isPasswordValid
            binding.rememberMe.isChecked = state.rememberMe
            if (state.isLoading) {
                binding.loginButton.text = "loading ... "
            } else {
                binding.loginButton.text = getString(R.string.log_in)
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.collectFlow(loginViewModel.sideEffect) { effect ->
            when (effect) {
                is LoginSideEffect.ShowError -> {
                    Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                }
                is LoginSideEffect.NavigateToHome -> {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHome2())
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
            val email = bundle.getString("email") ?: ""
            val password = bundle.getString("password") ?: ""
            binding.emailInputLogin.setText(email)
            binding.passwordInputLogin.setText(password)
            loginViewModel.processIntent(LoginIntent.EmailChanged(email))
            loginViewModel.processIntent(LoginIntent.PasswordChanged(password))
        }
    }
}