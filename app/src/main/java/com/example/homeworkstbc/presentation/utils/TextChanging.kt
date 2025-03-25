package com.example.homeworkstbc.presentation.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun EditText.afterTextChangedDebounced(
    scope: CoroutineScope,
    debounceMillis: Long = 300L,
    action: (String) -> Unit
) {
    var job: Job? = null
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            job?.cancel()
            job = scope.launch {
                delay(debounceMillis)
                action(s.toString())
            }
        }
    })
}
fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}