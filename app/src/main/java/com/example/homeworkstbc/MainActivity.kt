package com.example.homeworkstbc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeworkstbc.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val user = auth.currentUser
        if (user != null) {
            Toast.makeText(this, "მოგესალმებით ${user.email}", Toast.LENGTH_SHORT).show()
            binding.userEmail.text = user.email
        }

        binding.registerButtonMain.setOnClickListener {
            navigateToFirstRegister()
        }
        binding.loginButtonMain.setOnClickListener {
            navigateToLoginScreen()
        }

    }

    private val loginActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == RESULT_OK){
            binding.userEmail.text = result.data?.getStringExtra("userEmail")
        }
    }

    private fun navigateToFirstRegister ( ) {
        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToLoginScreen ( ) {
        val intent = Intent(this,LogInActivity::class.java)
        loginActivity.launch(intent)
    }






}