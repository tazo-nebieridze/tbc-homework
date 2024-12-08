package com.example.homeworkstbc

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeworkstbc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val users = mutableMapOf<String,String>();

        val userNameInput = binding.usernameInput
        val emailInput = binding.emailInput
        val currentUsers = binding.currentUsers
        val addUsersButton = binding.save

        currentUsers.text = "CURRENT USERS ${users.size}"

        fun isUniqueEmail (email : String) : Boolean {
            return users.containsKey(email)
        }

        addUsersButton.setOnClickListener {
            if(userNameInput.text?.length == 0 && emailInput.text?.length == 0){
                Toast.makeText(this,"გთხოვთ შეავსოთ ყველა ველი",Toast.LENGTH_SHORT).show()
            } else if(userNameInput.text?.length == 0) {
                Toast.makeText(this,"გთხოვთ შეავსოთ USERNAME ველი",Toast.LENGTH_SHORT).show()

            } else if(emailInput.text?.length == 0 ){
                Toast.makeText(this,"გთხოვთ შეავსოთ EMAIl ველი",Toast.LENGTH_SHORT).show()

            } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.text).matches()){
                Toast.makeText(this,"გთხოვთ შეიყვანოთ ვალიდური მეილი",Toast.LENGTH_SHORT).show()

            } else if(isUniqueEmail(emailInput.text.toString())){
            Toast.makeText(this,"ეს მეილი უკვე გამოყენებულია",Toast.LENGTH_SHORT).show()

        } else {
                users.put(emailInput.text.toString(),userNameInput.text.toString())
                currentUsers.text = "CURRENT USERS ${users.size}"
                emailInput.text?.clear()
                userNameInput.text?.clear()
            }
        }


        val emailInputSearch = binding.emailInputSearch

        val searchButton = binding.getInfo

        val usernameInfo = binding.usernameInfo

        val emailInfo = binding.emailInfo

        val infoView = binding.infoView

        val infoView404 = binding.infoView404

        fun isFoundUser (email : String) : Boolean {
            return users.containsKey(email)
        }

        searchButton.setOnClickListener {
            if(emailInputSearch.text?.length == 0){
                Toast.makeText(this,"გთხოვთ შეავსოთ EMAIl ველი",Toast.LENGTH_SHORT).show()
            } else if( !android.util.Patterns.EMAIL_ADDRESS.matcher(emailInputSearch.text).matches() ){
                Toast.makeText(this,"გთხოვთ შეიყვანოთ ვალიდური მეილი",Toast.LENGTH_SHORT).show()

            } else  if(isFoundUser(emailInputSearch.text.toString())){
                infoView.visibility = View.VISIBLE
                infoView404.visibility =View.GONE
                emailInfo.text = emailInputSearch.text
                usernameInfo.text = users[emailInputSearch.text.toString()]
            } else {
                infoView.visibility = View.GONE
                infoView404.visibility =View.VISIBLE
            }
        }



    }


}