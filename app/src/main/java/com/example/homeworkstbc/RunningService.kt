package com.example.homeworkstbc

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.example.homeworkstbc.presentation.base.MainActivity

class RunningService : Service() {

    private var timer: CountDownTimer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopServiceProperly()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        // Create PendingIntent to open MainActivity
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build initial notification
        val notification = NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
            .setSmallIcon(R.drawable.heart)
            .setContentTitle("Timer Service")
            .setContentText("01:00")
            .setContentIntent(pendingIntent)
            .build()

        // Start foreground service
//        startForeground(1, notification)

        ServiceCompat.startForeground(
            this,
            1,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
        )

        // Start the countdown timer
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = formatTime(millisUntilFinished)
                val updatedNotification = NotificationCompat.Builder(this@RunningService, getString(R.string.default_notification_channel_id))
                    .setSmallIcon(R.drawable.heart)
                    .setContentTitle("Timer Service")
                    .setContentText(timeLeft)
                    .setContentIntent(pendingIntent)
                    .build()
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.notify(1, updatedNotification)
            }

            override fun onFinish() {
                stopServiceProperly()
            }
        }.start()
    }

    private fun stopServiceProperly() {
        stopForeground(true) // Remove notification
        stopSelf() // Stop the service
    }

    override fun onDestroy() {
        timer?.cancel() // Cancel timer to prevent updates after service stops
        super.onDestroy()
    }

    private fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / 1000) / 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    enum class Actions {
        START, STOP
    }
}