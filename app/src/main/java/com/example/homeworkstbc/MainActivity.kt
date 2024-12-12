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

        setUpTexts()

    }

    private fun setUpTexts () {
        binding.leftArrow.alpha = 0.5f
        binding.bookmark.alpha = 0.5f
        binding.mountains.setText(R.string.andes_mountain)
        binding.location.setText(R.string.south_america)
        binding.price.setText(R.string.price)
        binding.dollar.setText(R.string.dollar)
        binding.priceAmount.setText(R.string.price_amount)
        binding.overview.setText(R.string.overview)
        binding.details.setText(R.string.detail)
        binding.hours.setText(R.string.hours)
        binding.cel.setText(R.string.cel)
        binding.startsAmount.setText(R.string.starts_amount)
        binding.description.setText(R.string.description)
        binding.bookButton.setText(R.string.book_button)
    }






}