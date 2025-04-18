package com.example.newsapp.feature.main.view

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.newsapp.R
import com.example.newsapp.core.session.PreferenceProvider
import com.example.newsapp.feature.login.view.LoginActivity
import org.koin.core.context.GlobalContext

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class LogoutReceiver : BroadcastReceiver() {
    private val preferenceProvider: PreferenceProvider get() = GlobalContext.get().get()

    override fun onReceive(context: Context, intent: Intent?) {
        preferenceProvider.clearAuthPreferences()
        showLogoutNotification(context)

        // Mulai LoginActivity
        val loginIntent = Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(loginIntent)
    }

    @SuppressLint("NotificationPermission")
    private fun showLogoutNotification(context: Context) {
        val channelId = "logout_channel"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Logout Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Sesi Berakhir")
            .setContentText("Kamu telah logout otomatis setelah 10 menit.")
            .setSmallIcon(R.drawable.ic_news_app)
            .setAutoCancel(true)
            .build()

        manager.notify(101, notification)
    }
}