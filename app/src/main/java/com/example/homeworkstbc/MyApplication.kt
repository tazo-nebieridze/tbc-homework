package com.example.homeworkstbc

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    private val TAG = "MyApplication"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        getToken()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.default_notification_channel_name)
            val description = getString(R.string.default_notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                getString(R.string.default_notification_channel_id),
                name,
                importance
            ).apply {
                this.description = description
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM token failed", task.exception)
                // Retry after 5 seconds
                Handler(Looper.getMainLooper()).postDelayed({
                    getToken()
                }, 5000)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d(TAG, "FCM Token: $token")
            Toast.makeText(this, "Token: $token", Toast.LENGTH_SHORT).show()
            // TODO: Send token to your server here (e.g., via API call)
        }
    }
}