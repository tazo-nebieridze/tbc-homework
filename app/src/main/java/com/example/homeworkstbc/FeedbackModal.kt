import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homeworkstbc.Item
import com.example.homeworkstbc.databinding.LayoutFeedbackModalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FeedbackModal(private val item: Item, private val onFeedbackSubmitted: (String) -> Unit) :
    BottomSheetDialogFragment() {

    private var _binding: LayoutFeedbackModalBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutFeedbackModalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
        onSubmit()

    }
    private fun setUp ( ) {
        binding.itemName.text = item.name
        binding.itemDetails.text = "${item.colorName} | Qty = ${item.quantity}"
        binding.itemPrice.text = "$ ${item.totalPrice}"
        binding.itemImage.setImageResource(item.picture)

        binding.cancelButton.setOnClickListener { dismiss() }
    }

    private fun onSubmit ( ) {
        binding.submitButton.setOnClickListener {
            val feedback = binding.feedbackInput.text.toString()
            if (feedback.isNotBlank()) {
                onFeedbackSubmitted.invoke(feedback)
                dismiss()
            } else {
                binding.feedbackInput.error = "Feedback cannot be empty"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
