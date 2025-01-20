package com.example.homeworkstbc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.models.Chat
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentViewModel : ViewModel() {

    private val json = """
        [
            {
                "id": 1,
                "image": "https://www.alia.ge/wp-content/uploads/2022/09/grisha.jpg",
                "owner": "grisha oniani",
                "last_message": "თავის ტერიტორიას ბომბავდა",
                "last_active": "4:20 PM",
                "unread_messages": 3,
                "is_typing": false,
                "laste_message_type": "text"
            },
            {
                "id": 2,
                "image": null,
                "owner": "jemal kakauridze",
                "last_message": "შემოგევლე",
                "last_active": "3:00 AM",
                "unread_messages": 0,
                "is_typing": true,
                "laste_message_type": "voice"
            },
            {
                "id": 3,
                "image": "https://i.ytimg.com/vi/KYY0TBqTfQg/hqdefault.jpg",
                "owner": "guram jenoria",
                "last_message": "ცოცხალი ვარ მა რა ვარ შე.. როდის იყო კვტარი ტელეფონზე ლაპარაკობდა",
                "last_active": "1:00 ",
                "unread_messages": 0,
                "is_typing": false,
                "laste_message_type": "file"
            },
            {
                "id": 4,
                "image": "",
                "owner": "kako kakoshvili",
                "last_message": "ადამიანი რო მოსაკლავად გაგიმეტებს თანაც ქალი ის დასანდობი არ არი",
                "last_active": "1:00 PM",
                "unread_messages": 0,
                "is_typing": false,
                "laste_message_type": "text"
            }
        ]
    """

    private val _chatList = MutableLiveData<List<Chat>>()
    val chatList: LiveData<List<Chat>> get() = _chatList

    private val _filteredChatList = MutableLiveData<List<Chat>>()
    val filteredChatList: LiveData<List<Chat>> get() = _filteredChatList


    init {
        parseJson()
    }

    private fun parseJson() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

                val type = Types.newParameterizedType(List::class.java, Chat::class.java)

                val adapter = moshi.adapter<List<Chat>>(type)

                val parsedChatList = adapter.fromJson(json)

                withContext(Dispatchers.Main) {
                    _chatList.value = parsedChatList!!
                    _filteredChatList.value = parsedChatList!!
                }
            } catch (e: Exception) {
                Log.e("FragmentViewModel", "Error parsing JSON", e)
            }
        }
    }

    fun filterChats(query: String) {
        if (query.isBlank()) {
            _filteredChatList.value = _chatList.value
        } else {
            val queryLowerCase = query.lowercase()
            val filteredList = _chatList.value?.filter {
                it.owner.lowercase().contains(queryLowerCase)
            } ?: emptyList()
            _filteredChatList.value = filteredList
        }
    }
}
