package com.example.homeworkstbc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class Input(
    @SerialName("field_id")
    val fieldId: Int,
    val hint: String? = null,
    @SerialName("field_type")
    val fieldType:String? = null,
    val keyboard:String? = null,
    val required:Boolean,
    @SerialName("is_active")
    val isActive:Boolean? = null,
    val icon:String? = null
)
