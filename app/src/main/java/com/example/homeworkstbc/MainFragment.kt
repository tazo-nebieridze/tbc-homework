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
        updateAdapter()
        listenToDialogResult()
        newCard()
        listenToFragmentResult()
    }

    private fun navigateToDialog (cardId: String ) {
        val action = MainFragmentDirections.actionMainFragmentToDeleteCardDialogFragment(cardId)
        findNavController().navigate(action)
    }

    private fun setUpViewPager() {
        cardAdapter = CardAdapter { cardId ->
            navigateToDialog(cardId)
        }

        binding.viewPager.adapter = cardAdapter
        binding.viewPager.setPageTransformer { page, position ->
            page.scaleY = 1 - (0.2f * Math.abs(position))
        }
    }

    private fun updateAdapter() {
        val cards = cardViewModel.getCards()
        cardAdapter.submitList(cards.toList())
    }

    private fun listenToDialogResult() {
        parentFragmentManager.setFragmentResultListener("deleteCardResult", this) { _, bundle ->
            val cardId = bundle.getString("cardId")
            val isDeleted = bundle.getBoolean("isDeleted", false)

            if (cardId != null && isDeleted) {
                cardViewModel.deleteCard(cardId)
                updateAdapter()
            }
        }
    }

    private fun newCard() {
        binding.addNewCard.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToNewCard()
            findNavController().navigate(action)
        }
    }

    private fun listenToFragmentResult() {
        parentFragmentManager.setFragmentResultListener("newCardResult", this) { _, bundle ->
            val newCard = bundle.getParcelable<Card>("newCard")
            if (newCard != null) {
                cardViewModel.addCard(newCard)
                updateAdapter()
            }
        }
    }
}