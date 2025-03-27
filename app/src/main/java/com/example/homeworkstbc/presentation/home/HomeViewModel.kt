package com.example.homeworkstbc.presentation.home

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import android.util.Base64
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    fun processImage(bitmap: Bitmap, onProcessed: (String?) -> Unit) {
        viewModelScope.launch {
            val base64 = withContext(Dispatchers.IO) {
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
            }
            onProcessed(base64)
        }
    }
}