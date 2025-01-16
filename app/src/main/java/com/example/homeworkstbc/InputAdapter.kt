import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkstbc.Input

import com.example.homeworkstbc.databinding.DatepickerChooserBinding
import com.example.homeworkstbc.databinding.GenderChooserBinding
import com.example.homeworkstbc.databinding.InputRecyclerBinding
import java.util.Calendar



class ItemsDiffUtil : DiffUtil.ItemCallback<Input>() {

    override fun areItemsTheSame(oldItem: Input, newItem: Input): Boolean {
        return oldItem.fieldId == newItem.fieldId
    }

    override fun areContentsTheSame(
        oldItem: Input,
        newItem: Input
    ): Boolean {
        return oldItem.fieldId == newItem.fieldId
    }

}


class InputAdapter(
    private val saveValues : (Int,String) -> Unit
) : ListAdapter<Input, RecyclerView.ViewHolder>(ItemsDiffUtil()) {

    companion object {
        const val EDIT_TEXT = 1
        const val DATE_PICKER = 2
        const val GENDER_PICKER = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).fieldType) {
            "input" -> EDIT_TEXT
            "chooser" -> {
                if (getItem(position).hint == "Birthday") DATE_PICKER
                else GENDER_PICKER
            }
            else -> EDIT_TEXT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EDIT_TEXT -> CurrentViewHolder(
                InputRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            DATE_PICKER -> DatePickerViewHolder(
                DatepickerChooserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            GENDER_PICKER -> GenderPickerViewHolder(
                GenderChooserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> CurrentViewHolder(
                InputRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrentViewHolder -> holder.onBind(position)
            is DatePickerViewHolder -> holder.onBind(position)
            is GenderPickerViewHolder -> holder.onBind(position)
        }
    }

    inner class CurrentViewHolder(private val binding: InputRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val input = getItem(position)
            binding.root.hint = input.hint
            binding.root.inputType = when (input.keyboard) {
                "text" -> android.text.InputType.TYPE_CLASS_TEXT
                "number" -> android.text.InputType.TYPE_CLASS_NUMBER
                else -> android.text.InputType.TYPE_CLASS_TEXT
            }


            binding.root.doOnTextChanged { text, start, before, count ->

                saveValues(input.fieldId,text.toString())

                }
        }
    }

    inner class DatePickerViewHolder(private val binding: DatepickerChooserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val input = getItem(position)
            binding.btnDatePicker.setOnClickListener {
                val context = binding.root.context
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val selectedDate = "$dayOfMonth/${month + 1}/$year"
                        binding.tvSelectedDate.text = selectedDate
                        saveValues(input.fieldId,selectedDate)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    inner class GenderPickerViewHolder(private val binding: GenderChooserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val input = getItem(position)
            val context = binding.root.context
            val genders = listOf("Male", "Female")

            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                genders
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerGender.adapter = adapter

            binding.spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if(position >= 0){
                        saveValues(input.fieldId,genders[position])
                        }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }
}
