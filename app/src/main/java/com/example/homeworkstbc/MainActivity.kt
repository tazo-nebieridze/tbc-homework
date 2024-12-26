package com.example.homeworkstbc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeworkstbc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var isEdit: Boolean = false

    var locationId : Int = -1

     val locations = mutableListOf(
        Location(
        1,
     "My Office",
    "SBI Building, street 3, Software Park",
        R.drawable.outline_domain_24
        ),
        Location(
        2,
        "My Home",
    "SBI Building, street 4, Software Park",
            R.drawable.outline_home_24
        )
     )

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

        val mainFragment = MainFragment()

        transaction.replace(R.id.main, mainFragment, "mainFragment")
        transaction.addToBackStack("mainFragment")
        transaction.commit()
    }

    data class Location(
        val id: Int,
        var name: String,
        var location: String,
        val icon: Int
    )

}
