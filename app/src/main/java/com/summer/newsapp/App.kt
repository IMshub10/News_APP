package com.summer.newsapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val newsChannel =
            NotificationChannel(
                newsServiceChannel,
                newsServiceChannel,
                NotificationManager.IMPORTANCE_LOW
            )
        newsChannel.description = "News Update"
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(newsChannel)
    }

    companion object {
        const val newsServiceChannel = "NewsServiceChannel"
    }
}