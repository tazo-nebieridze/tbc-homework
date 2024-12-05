package com.example.mysecondapp

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        val emailField : AppCompatEditText = findViewById(R.id.emailInput)
        val usernameField : AppCompatEditText = findViewById(R.id.usernameInput)
        val firstNameField : AppCompatEditText  = findViewById(R.id.firstNameInput)
        val lastNameField : AppCompatEditText  = findViewById(R.id.lastNameInput)
        val ageField : AppCompatEditText  = findViewById(R.id.ageInput)

        val saveButton : AppCompatButton = findViewById(R.id.save)
        val clearButton : AppCompatButton = findViewById(R.id.clear)

        val emailTextView: AppCompatTextView = findViewById(R.id.emailText)
        val usernameTextView: AppCompatTextView = findViewById(R.id.usernameText)
        val fullNameTextView: AppCompatTextView = findViewById(R.id.fullNameText)
        val ageTextView: AppCompatTextView = findViewById(R.id.ageText)
        val againButton: AppCompatButton = findViewById(R.id.againButton)

        val inputFieldsLayout: LinearLayoutCompat = findViewById(R.id.inputFieldsLayout)
        val outputFieldsLayout: LinearLayoutCompat = findViewById(R.id.outputFieldsLayout)


        saveButton.setOnClickListener {
            val email = emailField.text.toString()
            val username = usernameField.text.toString()
            val firstName = firstNameField.text.toString()
            val lastName = lastNameField.text.toString()
            val age = ageField.text.toString()

            when {
                email.isEmpty() || username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() -> {
                    Toast.makeText(this, "გთხოვთ შეავსეთ ყველა ველი", Toast.LENGTH_SHORT).show()
                }
                username.length < 10 -> {
                    Toast.makeText(this, "Username უნდა შეიცავდეს მინიმუმ 10 ასოს", Toast.LENGTH_SHORT).show()
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "გთხოვთ შეიყვანოთ ვალიდური მეილი", Toast.LENGTH_SHORT).show()
                }
                age.toInt() <= 0 -> {
                    Toast.makeText(this, "ასაკი უნდა იყოს დადებითი რიცხვი", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    inputFieldsLayout.visibility = View.GONE
                    outputFieldsLayout.visibility = View.VISIBLE

                    emailTextView.text = "შენი მეილი არის : $email"
                    usernameTextView.text = "შენი Username არის : $username"
                    fullNameTextView.text = "შენი სახელი და გვარი არის : $firstName $lastName"
                    ageTextView.text = "შენი ასაკი არის : $age"
                }
            }
        }


        againButton.setOnClickListener {
            inputFieldsLayout.visibility = View.VISIBLE
            outputFieldsLayout.visibility = View.GONE

            emailField.text?.clear()
            usernameField.text?.clear()
            firstNameField.text?.clear()
            lastNameField.text?.clear()
            ageField.text?.clear()
        }

        clearButton.setOnLongClickListener {
            emailField.text?.clear()
            usernameField.text?.clear()
            firstNameField.text?.clear()
            lastNameField.text?.clear()
            ageField.text?.clear()
            Toast.makeText(this, "ყველა ველი გასუთავებულია", Toast.LENGTH_SHORT).show()
            true
        }
    }


}
