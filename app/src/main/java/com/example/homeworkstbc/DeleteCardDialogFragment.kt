package com.example.homeworkstbc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.homeworkstbc.databinding.FragmentDeleteCardDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeleteCardDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDeleteCardDialogBinding? = null
    private val binding get() = _binding!!
    private val args: DeleteCardDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteCardDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleReject()
        handleAccept()

    }

    private fun handleAccept ( ) {
        binding.btnYes.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                "deleteCardResult",
                Bundle().apply {
                    putString("cardId", args.cardId)
                    putBoolean("isDeleted", true)
                }
            )

            dismiss()
        }
    }

    private fun handleReject ( ) {
        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
