package com.example.homeworkstbc.utils

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    var token: String? = null
    var email: String? = null
    var expirationTime: Long? = null

    fun clearSession() {
        token = null
        email = null
        expirationTime = null
    }
}
