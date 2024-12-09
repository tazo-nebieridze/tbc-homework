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


        val anagramInput = binding.anagramInput
        val saveButton = binding.save
        val outputButton = binding.output
        val anagramCount = binding.anagramsCount
        val clearButton = binding.clear

        val anagramList = mutableListOf<String>();


        fun groupAnagrams(anagrams: List<String>): List<List<String>> {
            val anagramMap = mutableMapOf<String, MutableList<String>>()

            for( anagram in anagrams){
                val key = anagram.toList().sorted().joinToString("")

                if(anagramMap.containsKey(key)){

                    anagramMap[key]?.add(key)

                } else {
                    anagramMap[key] = mutableListOf(anagram)
                }

            }

            return anagramMap.values.toList()
        }




        saveButton.setOnClickListener {
            if(anagramInput.text?.length == 0){
                Toast.makeText(this,"გთხოვთ შეავსეთ ველი",Toast.LENGTH_SHORT).show();
            } else {
                anagramList.add(anagramInput.text.toString())
                anagramInput.text?.clear()


            }
        }

        outputButton.setOnClickListener {
            anagramCount.text = "ANAGARAMS: ${groupAnagrams(anagramList).size}"
        }


        clearButton.setOnClickListener {
            anagramInput.text?.clear()
            anagramCount.text="ANAGARAMS: 0"
            anagramList.clear()
        }






    }

    override fun onStart() {
        super.onStart()
        println("onStart called")
    }

    override fun onResume() {
        super.onResume()
        println("onResume called")
    }

    override fun onPause() {
        super.onPause()
        println("onPause called")
    }

    override fun onStop() {
        super.onStop()
        println("onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy called")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart called")
    }


}