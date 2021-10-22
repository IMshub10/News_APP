package com.summer.newsapp.data.repository

import android.app.Application
import com.summer.newsapp.data.api.apihelper.NewsHelper
import com.summer.newsapp.data.room.dao.NewsDao
import com.summer.newsapp.data.room.database.NewsDatabase
import com.summer.newsapp.data.room.model.NewsEntity
import com.summer.newsapp.utils.Constants
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewsRepository(
    application: Application,
    private val apiHelper: NewsHelper,
) {
    private val newsDao: NewsDao

    init {
        val newsDatabase = NewsDatabase.getInstance(application)
        newsDao = newsDatabase.getNewsDao()
    }

    private fun ignoredInsert(newsEntity: NewsEntity) =
        newsDao.insert(newsEntity)

    fun update(newsEntity: NewsEntity) =
        newsDao.update(newsEntity)

    fun delete(id: String) =
        newsDao.delete(id)

    fun updateArticle(id: String, read: Boolean) =
        newsDao.updateArticle(id,read)

    fun getAllArticles(read:Boolean) =
        newsDao.getAllArticles(read)

    suspend fun getAllArticlesFromRetrofit(): Boolean {
        val dateFormat = SimpleDateFormat(Constants.API_PROVIDING_DATE_FORMAT, Locale.US)
        val response = apiHelper.getAllArticles()
        for (article in response.articles) {
            try {
                val date = dateFormat.parse(article.publishedAt)
                if (date != null) {
                    ignoredInsert(
                        NewsEntity(
                            article.author.orEmpty() + date.time.toString(),
                            article.title.orEmpty(),
                            article.description.orEmpty(),
                            article.content.orEmpty(),
                            article.urlToImage.orEmpty(),
                            article.url.orEmpty(),
                            article.publishedAt,
                            date.time,
                            false
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return response.status == "ok"
    }
}