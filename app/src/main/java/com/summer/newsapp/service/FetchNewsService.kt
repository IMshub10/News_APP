package com.summer.newsapp.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.kwabenaberko.newsapilib.NewsApiClient
import com.summer.newsapp.R
import com.summer.newsapp.data.api.repository.NewsApiRepository
import com.summer.newsapp.data.room.repository.NewsDBRepository
import com.summer.newsapp.ui.activities.MainActivity
import com.summer.newsapp.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class FetchNewsService : Service() {
    private val newsServiceChannel = "NewsServiceChannel"
    private lateinit var newsApiRepository:NewsApiRepository

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        newsApiRepository = NewsApiRepository(NewsDBRepository(application))
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification =
            NotificationCompat.Builder(applicationContext, newsServiceChannel)
                .setSmallIcon(R.drawable.ic_moon_stars)
                .setContentTitle("Fetching news updates")
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .setContentIntent(pendingIntent)
                .setSound(null)
                .build()
        startForeground(100, notification)
        newsApiRepository.updateNewsInLocalDB(
            NewsApiClient(Constants.NEWS_APP_API_KEY),
            SimpleDateFormat(Constants.API_PROVIDING_DATE_FORMAT, Locale.getDefault())
        )
        return START_STICKY
    }
}