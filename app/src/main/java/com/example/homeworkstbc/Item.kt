package com.example.homeworkstbc

data class Item(
    val id: String,
    val name: String,
    val color: Int,
    val colorName: String,
    val totalPrice: Int,
    val picture: Int,
    val quantity: Int,
    val status: StatusType,
    var feedBack: String?
)
