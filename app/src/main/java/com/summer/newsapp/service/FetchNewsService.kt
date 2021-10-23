package com.summer.newsapp.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.summer.newsapp.R
import com.summer.newsapp.data.repository.NewsRepository
import com.summer.newsapp.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FetchNewsService : Service() {
    private val TAG = "FetchNewsService"

    @Inject
    lateinit var newsRepository: NewsRepository

    private val newsServiceChannel = "NewsServiceChannel"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        initFetchArticles(newsRepository)
        stopSelf()
        return START_STICKY
    }

    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification =
            NotificationCompat.Builder(applicationContext, newsServiceChannel)
                .setSmallIcon(R.drawable.ic_moon_stars)
                .setContentTitle("Fetching news updates")
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .setContentIntent(pendingIntent)
                .setSound(null)
                .build()
        startForeground(100, notification)
    }

    private fun initFetchArticles(repository: NewsRepository) {
        CoroutineScope(Dispatchers.IO).launch {
            val status = repository.getAllArticlesFromRetrofit()
            Log.e(TAG, status.toString())
        }
    }

}