package com.example.homeworkstbc

import java.util.Date

data class Message(
    val id:String,
    val text:String,
    val date: Date,
    val sender : SenderType
) {
}