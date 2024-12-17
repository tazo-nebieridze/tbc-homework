package com.example.homeworkstbc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeworkstbc.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val userMap = mutableMapOf<String, User>()
    private var deletedUsersCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.addUser.setOnClickListener {
            addUser()
        }
        binding.deleteUser.setOnClickListener {
            removeUser()
        }
        binding.editUser.setOnClickListener {
            updateUser()
        }
        updateActiveUsersCount()
        updateDeletedUsersCount()
    }

    private fun addUser() {
        val email = binding.userEmailText.text.toString().trim()
        val name = binding.userName.text.toString().trim()
        val lastName = binding.userLastName.text.toString().trim()
        val age = binding.useAge.text.toString().trim()

        if (email.isEmpty() || name.isEmpty() || lastName.isEmpty() || age.isEmpty()) {
            Toast.makeText(this, "გთხოვთ შეავსეთ ყველა ველი", Toast.LENGTH_SHORT).show()
            return
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "გთხოვთ შეიყვანეთ ვალიდური მეილი", Toast.LENGTH_SHORT).show()
            return
        }

        if (userMap.containsKey(email)) {
            binding.messageText.text = "User already exists"
            binding.messageText.setTextColor(resources.getColor(R.color.red))
//            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
        } else {
            userMap[email] = User(email, name, lastName, age.toInt())
            binding.messageText.text = "User added successfully"
            binding.messageText.setTextColor(resources.getColor(R.color.green))
//            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
            binding.userEmailText.text?.clear()
            binding.userName.text?.clear()
            binding.userLastName.text?.clear()
            binding.useAge.text?.clear()
        }
        updateActiveUsersCount()
        println(userMap)
    }

    private fun removeUser() {
        val email = binding.userEmailText.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(this, "გთხოვთ შეიყვანეთ წასაშლელი მომხმარებლის მეილი", Toast.LENGTH_SHORT).show()
            return
        }

        if (userMap.containsKey(email)) {
            userMap.remove(email)
            deletedUsersCount++
            updateDeletedUsersCount()
            binding.messageText.text = "User deleted successfully"
            binding.messageText.setTextColor(resources.getColor(R.color.green))
//            Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show()
            binding.userEmailText.text?.clear()
            binding.userName.text?.clear()
            binding.userLastName.text?.clear()
            binding.useAge.text?.clear()
        } else {
            binding.messageText.text = "User does not exist"
            binding.messageText.setTextColor(resources.getColor(R.color.red))
//            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
        }
        println(userMap)
        updateActiveUsersCount()
    }

    private fun updateUser() {
        val email = binding.userEmailText.text.toString().trim()
        val name = binding.userName.text.toString().trim()
        val lastName = binding.userLastName.text.toString().trim()
        val age = binding.useAge.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(this, "გთხოვთ შეიყვანოთ სასურველი მომხმარებლის მეილი", Toast.LENGTH_SHORT).show()
            return
        }

        if (userMap.containsKey(email)) {
            val newName = name.ifEmpty { userMap[email]?.name ?: "" }
            val newLastName = lastName.ifEmpty { userMap[email]?.lastName ?: "" }
            val newAge = age.ifEmpty { userMap[email]?.age  ?: 0}.toString().toInt()

            val updatedUser = User(
                email,
                newName,
                newLastName,
                newAge
            )
            userMap[email] = updatedUser
            binding.messageText.text = "User updated successfully"
            binding.messageText.setTextColor(resources.getColor(R.color.green))
//            Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
            binding.userEmailText.text?.clear()
            binding.userName.text?.clear()
            binding.userLastName.text?.clear()
            binding.useAge.text?.clear()
        } else {
            binding.messageText.text = "User does not exist"
            binding.messageText.setTextColor(resources.getColor(R.color.red))
//            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
        }
        println(userMap)

    }

    private fun updateActiveUsersCount ( ) {
        binding.activaUsers.text = "Active Users :${userMap.size}"
    }
    private fun updateDeletedUsersCount() {
        binding.deletedUsers.text = "Deleted Users: $deletedUsersCount"
    }


    data class User(
        val email: String,
        var name: String,
        var lastName: String,
        var age: Int
    )
}
