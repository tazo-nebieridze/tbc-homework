package com.example.homeworkstbc

import Card
import CardAdapter
import CardViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.homeworkstbc.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {



    private val cardViewModel: CardViewModel by viewModels()
    private lateinit var cardAdapter: CardAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()

        listenToDialogResult()
        newCard()
        listenToFragmentResult()
    }

    private fun listenToDialogResult() {
        parentFragmentManager.setFragmentResultListener("deleteCardResult", this) { _, bundle ->
            val cardId = bundle.getString("cardId")
            val isDeleted = bundle.getBoolean("isDeleted", false)

            if (cardId != null && isDeleted) {
                cardViewModel.deleteCard(cardId)
            }
        }
    }

    private fun setUpViewPager() {
        cardAdapter = CardAdapter { cardId ->
            val action = MainFragmentDirections.actionMainFragmentToDeleteCardDialogFragment(cardId)
            findNavController().navigate(action)
        }

        binding.viewPager.adapter = cardAdapter
        binding.viewPager.setPageTransformer { page, position ->
            page.scaleY = 1 - (0.2f * Math.abs(position))
        }

        cardViewModel.cards.observe(viewLifecycleOwner) { updatedCards ->
            cardAdapter.submitList(updatedCards)
        }
    }

    private fun newCard ( ) {
        binding.addNewCard.setOnClickListener {
            Log.e("vendeta", "11111111")

            val action = MainFragmentDirections.actionMainFragmentToNewCard()

            findNavController().navigate(action)

        }
    }
    private fun listenToFragmentResult() {
        parentFragmentManager.setFragmentResultListener("newCardResult", this) { _, bundle ->
            val newCard = bundle.getParcelable<Card>("newCard")
            if (newCard != null) {
                cardViewModel.addCard(newCard)
            }
        }
    }

}
