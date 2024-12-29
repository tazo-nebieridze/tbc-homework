package com.example.homeworkstbc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeworkstbc.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    var orders = mutableListOf(
        Order(
            1514,
            "IK987362341",
            "13/05/21",
            2,
            210,
            "PENDING"
        ),
        Order(
            1515,
            "IK987362342",
            "14/10/21",
            3,
            110,
            "PENDING"
        ),
        Order(
            1516,
            "IK987362343",
            "08/01/21",
            1,
            80,
            "PENDING"
        ),
        Order(
            1517,
            "IK987362344",
            "08/01/31",
            3,
            183,
            "DELIVERED"
        ),
        Order(
            1518,
            "IK987362345",
            "08/05/26",
            2,
            93,
            "CANCELLED"
        ),
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


        transaction.replace(
            R.id.main,
            MainFragment.newInstance(param1 = "nika", param2 = "dasd"),
            "mainFragment")
        transaction.addToBackStack("mainFragment")
        transaction.commit()
    }

    data class Order(
        val orderCount : Int,
        val id:String,
        val createdAt: String,
        val itemsQuantity : Int,
        val payment : Int,
        var status: String

    )

}
