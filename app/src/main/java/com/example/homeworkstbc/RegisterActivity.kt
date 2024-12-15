package com.example.homeworkstbc

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeworkstbc.databinding.RegisterActivityLayoutBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: RegisterActivityLayoutBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = RegisterActivityLayoutBinding.inflate(layoutInflater)

        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.goBackToMain.setOnClickListener {
            goBackToMain()
        }

        nextStep()
        signUp()
    }

    private fun goBackToMain ( ) {
        finish()
    }

    private fun nextStep ( ) {
        binding.next.setOnClickListener {
            if(binding.userEmailText.text?.length == 0 || binding.userPassword.text?.length == 0){
                Toast.makeText(this,"გთხოვთ შეავსეთ ყველა ველი",Toast.LENGTH_SHORT).show()
            } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.userEmailText.text.toString()).matches()){
                Toast.makeText(this,"გთხოვთ შეიყვანოტ ვალიდური მეილი",Toast.LENGTH_SHORT).show()

            } else if(binding.userPassword.text?.length!! < 6 ){
                Toast.makeText(this,"პაროლი უნდა შეიცავდეს მინიმუმ 6 სიმბოლოს",Toast.LENGTH_SHORT).show()

            }else {
                binding.registerFirst.visibility = View.GONE
                binding.registerSecond.visibility = View.VISIBLE
            }
        }
    }

    private fun signUp() {
        binding.signUp.setOnClickListener {
            val email = binding.userEmailText.text.toString()
            val password = binding.userPassword.text.toString()
            val userName = binding.userNameText.text.toString()

            if (userName.isEmpty()) {
                Toast.makeText(this, "გთხოვთ შეავსეთ სახელის ველი", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "თქვენ წარმატებით დარეგისტრირდით", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "შეცდომა, სცადეთ თავიდან", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}