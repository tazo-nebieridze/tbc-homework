package com.example.homeworkstbc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeworkstbc.databinding.LoginLayoutBinding
import com.example.homeworkstbc.databinding.RegisterActivityLayoutBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: LoginLayoutBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = LoginLayoutBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.goBackToMain.setOnClickListener {
            goBackToMain()
        }

        nextStep()
    }

    private fun goBackToMain ( ) {
        finish()
    }

    private fun nextStep() {
        binding.next.setOnClickListener {
            val email = binding.userEmailText.text.toString()
            val password = binding.userPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "გთხოვთ შეავსეთ ყველა ველი", Toast.LENGTH_SHORT).show()
            } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this,"გთხოვთ შეიყვანოტ ვალიდური მეილი",Toast.LENGTH_SHORT).show()

            }  else if (password.length < 6) {
                Toast.makeText(this, "პაროლი უნდა შეიცავდეს მინიმუმ 6 სიმბოლოს", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent()
                            intent.putExtra("userEmail", email)
                            setResult(RESULT_OK, intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "შეცდომა, სცადეთ თავიდან",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}