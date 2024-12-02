package com.example.mysecondapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
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

        val editTextNumber : AppCompatEditText = findViewById(R.id.input)
        val languageToggle : SwitchCompat = findViewById(R.id.languageToggle)
        val convertButton : AppCompatButton = findViewById(R.id.Main_button)
        val resultTextView : AppCompatTextView = findViewById(R.id.result)
        var isEnglish = false

        languageToggle.setOnCheckedChangeListener { _, isChecked ->
            isEnglish = isChecked
        }

        fun numberToEnglish(number: Int): String {
            val ones = arrayOf(
                "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
                "seventeen", "eighteen", "nineteen"
            )
            val tens = arrayOf(
                 "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
            )

            val hundred = number / 100
            val rest = number % 100

            val hundredText = if (hundred > 0) "${ones[hundred - 1]} hundred" else ""
            val connector = if (hundred > 0 && rest > 0) " and " else ""
            var restText : String
            if (rest == 0) {
                restText = ""
            } else if (rest < 20) {
                restText = ones[rest - 1]
            } else {
                val tensPart = tens[(rest / 10) - 1]
                val onesPart = if (rest % 10 > 0) ones[(rest % 10) - 1] else ""
                restText = "$tensPart $onesPart".trim()
            }

            return "$hundredText$connector$restText".trim()
        }

        fun numberToGeorgian(number: Int): String {

            val cifrebi = listOf("ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა")
            val ateulebi = listOf("ათი", "ოცი", "ოცდაათი", "ორმოცი", "ორმოცდაათი", "სამოცი", "სამოცდაათი", "ოთხმოცი", "ოთხმოცდაათი")
            val ateulebi1 = listOf("თერთმეტი", "თორმეტი", "ცამეტი", "თოთხმეტი", "თხუთმეტი", "თექვსმეტი", "ჩვიდმეტი", "თვრამეტი", "ცხრამეტი")

            val aseulebi = listOf("ასი", "ორასი", "სამასი", "ოთხასი", "ხუთასი", "ექვსასი", "შვიდასი", "რვაასი", "ცხრაასი")

            val hundredPart = number / 100
            val tenPart = (number % 100) / 10
            val unitPart = number % 10

            var result : String = ""
            if(hundredPart != 0 && (tenPart == 0 && unitPart == 0)){
                result = result + aseulebi[hundredPart-1]
            } else if( hundredPart != 0 && (tenPart != 0 && unitPart == 0) ){
                result = result + aseulebi[hundredPart-1].dropLast(1) + ateulebi[tenPart-1]
            } else if (hundredPart != 0 && (tenPart == 0 && unitPart != 0)){
                result = result + aseulebi[hundredPart-1].dropLast(1) + cifrebi[unitPart-1]

            } else if (hundredPart != 0 && (tenPart != 0 && unitPart != 0) ){
                if(tenPart % 2 == 0 ){
                    result = result + aseulebi[hundredPart-1].dropLast(1) +
                            ateulebi[tenPart-1].dropLast(1) + "და" + cifrebi[unitPart-1]

                } else if(tenPart == 1) {
                    result = result + aseulebi[hundredPart-1].dropLast(1) +
                            ateulebi1[unitPart-1]
                } else {
                    result = result + aseulebi[hundredPart-1].dropLast(1) +
                            ateulebi[tenPart-2].dropLast(1) + "და" + ateulebi1[unitPart-1]
                }

            } else if(hundredPart == 0 && (tenPart == 0 && unitPart != 0)){
                result = result + cifrebi[unitPart-1]
            } else if(hundredPart == 0 && (tenPart != 0 && unitPart == 0)){
                result = result + ateulebi[tenPart-1]
            } else if(hundredPart == 0 && (tenPart != 0 && unitPart != 0)){
                if(tenPart % 2 == 0 ){
                    result = result + ateulebi[tenPart-1].dropLast(1) +
                             "და" + cifrebi[unitPart-1]

                } else
                 if(tenPart == 1) {
                    result = result  + ateulebi1[unitPart-1]

                } else {
                    result = result + ateulebi[tenPart-2].dropLast(1) +
                             "და" + ateulebi1[unitPart-1]
                }
            }

            return result
        }

        convertButton.setOnClickListener {
            val inputText = editTextNumber.text.toString()
            if (inputText.isNotEmpty()) {
                val number = inputText.toIntOrNull()

                if (number != null && number in 1 until 1000) {
                    val result = if (isEnglish) {
                        numberToEnglish(number)
                    } else {
                        numberToGeorgian(number)
                    }
                    resultTextView.text = result
                } else {
                    Toast.makeText(this, "გთხოვთ შეიყვანოთ რიცხვი 1-დან 1000-ის ჩათვლით", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "შეავსეთ ველი !", Toast.LENGTH_SHORT).show()
            }
        }



    }
}