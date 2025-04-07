package com.example.firstcomposeproject.presentation.auth.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.firstcomposeproject.R
import com.example.firstcomposeproject.presentation.utils.CollectSideEffect

@Composable
fun RegisterScreenContent(
    state: RegisterState,
    onIntent: (RegisterIntent) -> Unit,
    onBackClick: () -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var repeatPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            style = TextStyle(
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.1.sp
            ),
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.standing),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp)
                .padding(top = 40.dp)
                .height(250.dp)
        )
        TextField(
            value = state.email,
            onValueChange = { onIntent(RegisterIntent.EmailChanged(it)) },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp, bottom = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF4D700BEF),
                unfocusedContainerColor = Color(0xFF4D700BEF),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = state.password,
            onValueChange = { onIntent(RegisterIntent.PasswordChanged(it)) },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF4D700BEF),
                unfocusedContainerColor = Color(0xFF4D700BEF),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        TextField(
            value = state.repeatPassword,
            onValueChange = { onIntent(RegisterIntent.RepeatPasswordChanged(it)) },
            label = { Text("Repeat Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { repeatPasswordVisible = !repeatPasswordVisible }) {
                    Icon(
                        imageVector = if (repeatPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            visualTransformation = if (repeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF4D700BEF),
                unfocusedContainerColor = Color(0xFF4D700BEF),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(
            onClick = { onIntent(RegisterIntent.RegisterClicked) },
            enabled = state.isEmailValid && state.isPasswordValid && state.isRepeatPasswordValid && !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF700BEF))
        ) {
            Text(
                text = if (state.isLoading) "Loading..." else "Register", // Fixed button text
                color = Color.White,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account?",
                style = TextStyle(fontSize = 20.sp)
            )
            Text(
                text = " Login",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color(0xFF700BEF),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.clickable { onBackClick() }
            )
        }
    }
}

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: (String, String) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    CollectSideEffect(viewModel.sideEffect) { effect ->
        when (effect) {
            is RegisterSideEffect.ShowError -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                Log.d("RegisterScreen", "Error: ${effect.message}")
            }
            is RegisterSideEffect.NavigateToLogin -> {
                onRegisterSuccess(state.email, state.password)
            }
        }
    }

    RegisterScreenContent(
        state = state,
        onIntent = { intent -> viewModel.processIntent(intent) },
        onBackClick = onBackClick
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenContentPreview() {
    val fakeState = RegisterState(
        email = "example@email.com",
        password = "password123",
        isEmailValid = true,
        isPasswordValid = true,
        isLoading = false,
        repeatPassword = "password123",
        isRepeatPasswordValid = true
    )

    RegisterScreenContent (
        state = fakeState,
        onIntent = {  },
        onBackClick = {}
    )
}