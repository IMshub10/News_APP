package com.summer.newsapp.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.summer.newsapp.R
import com.summer.newsapp.data.api.RetrofitBuilder
import com.summer.newsapp.data.api.apihelper.NewsHelperImpl
import com.summer.newsapp.data.api.apiservice.NewsService
import com.summer.newsapp.data.repository.NewsRepository
import com.summer.newsapp.ui.activities.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FetchNewsService : Service() {
    private val newsServiceChannel = "NewsServiceChannel"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val newsRepository =
            NewsRepository(application, NewsHelperImpl(RetrofitBuilder.newsApiService))
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
        initFetchArticles(newsRepository)
        stopSelf()
        return START_STICKY
    }

    private fun initFetchArticles(repository: NewsRepository) {
        CoroutineScope(Dispatchers.IO).launch {
            val status = repository.getAllArticlesFromRetrofit()
            Log.e("Service", status.toString())
        }
    }

}