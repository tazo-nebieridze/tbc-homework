package com.example.homeworkstbc

import Card
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.homeworkstbc.databinding.FragmentNewCardBinding


class NewCard : BaseFragment<FragmentNewCardBinding>(FragmentNewCardBinding::inflate) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkMastercardByDefault()
        selectCardType()
        expireDataInput()
        cardHolderInput()
        cardNumberInput()
        addNewCard()
        goBack()
    }

    private fun goBack ( ) {
        binding.goBack.setOnClickListener {
            parentFragmentManager.popBackStack()

        }
    }

    private fun checkMastercardByDefault ( ) {
        binding.masterCardRadioButton.isChecked = true
    }

    private fun selectCardType ( ) {

        binding.masterCardRadioButton.setOnClickListener {
            if( binding.masterCardRadioButton.isChecked ) {
                binding.visaLogo.setImageResource(R.drawable.media)
                Log.e("vendeta", "2222222222")

            } else if ( binding.VisaRadioButton.isChecked ){
                Log.e("vendeta", "3333333333")


                binding.visaLogo.setImageResource(R.drawable.media1)
            }
        }
        binding.VisaRadioButton.setOnClickListener {
            if( binding.masterCardRadioButton.isChecked ) {
                binding.visaLogo.setImageResource(R.drawable.media)
                Log.e("vendeta", "2222222222")

            } else if ( binding.VisaRadioButton.isChecked ){
                Log.e("vendeta", "3333333333")


                binding.visaLogo.setImageResource(R.drawable.media1)
            }
        }

    }

    private fun expireDataInput() {
        binding.expiresDateInput.doOnTextChanged { text, _, _, _ ->
            val input = text?.toString()?.replace("/", "").orEmpty()
            if (input.isNotEmpty()) {
                val formatted = if (input.length > 2) {
                    "${input.substring(0, 2)}/${input.substring(2)}"
                } else {
                    input
                }

                if (binding.expiresDateInput.text.toString() != formatted) {
                    binding.expiresDateInput.setText(formatted)
                    binding.expiresDateInput.setSelection(formatted.length)
                }

                binding.validThruDate.text = formatted
            }
        }
    }

    private fun cardHolderInput( ) {
        binding.holderInput.doOnTextChanged { text, start, before, count ->
            binding.cardholderNameValue.text = text
        }
    }

    private fun cardNumberInput() {
        binding.cardNumberInput.doOnTextChanged { text, _, _, _ ->
            text?.let {
                val input = it.toString().replace(" ", "")
                val formatted = input.chunked(4).joinToString(" ")

                binding.cardNumber.text = formatted
            }
        }
    }

    private fun addNewCard() {
        binding.submitButton.setOnClickListener {
            if (binding.holderInput.text.isNullOrEmpty() ||
                binding.cardNumberInput.text?.length != 16 ||
                binding.cvvInput.text?.length != 3 ||
                binding.expiresDateInput.text?.length != 5
            ) {
                Toast.makeText(requireContext(), "Please finish building your card", Toast.LENGTH_SHORT).show()
            } else {
                val newCard = Card(
                    id = System.currentTimeMillis().toString(),
                    holder = binding.holderInput.text.toString(),
                    cardNumber = binding.cardNumberInput.text.toString().replace(" ", ""),
                    cvv = binding.cvvInput.text.toString().toInt(),
                    expiryMonth = binding.expiresDateInput.text.toString().substring(0, 2),
                    expiryYear = binding.expiresDateInput.text.toString().substring(3, 5),
                    cardType = if (binding.masterCardRadioButton.isChecked) CardType.MASTERCARD else CardType.VISA
                )

                parentFragmentManager.setFragmentResult(
                    "newCardResult",
                    Bundle().apply { putParcelable("newCard", newCard) }
                )

                parentFragmentManager.popBackStack()
            }
        }

    }




}