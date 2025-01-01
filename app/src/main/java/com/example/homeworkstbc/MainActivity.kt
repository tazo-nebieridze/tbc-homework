package com.example.homeworkstbc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeworkstbc.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        attachMainFragment()
    }

    private fun attachMainFragment() {
        val supportManager = supportFragmentManager
        val transaction = supportManager.beginTransaction()


        transaction.replace(
            R.id.main,
            MainFragment.newInstance(param1 = "nika", param2 = "dasd"),
            "mainFragment")
        transaction.addToBackStack("mainFragment")
        transaction.commit()
    }



}
