package com.summer.newsapp.data.repository

import com.summer.newsapp.data.api.apiservice.NewsService
import com.summer.newsapp.data.room.dao.NewsDao
import com.summer.newsapp.data.room.model.NewsEntity
import com.summer.newsapp.utils.Constants
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val newsApiService: NewsService,
) {

    private fun ignoredInsert(newsEntity: NewsEntity) =
        newsDao.insert(newsEntity)

    fun update(newsEntity: NewsEntity) =
        newsDao.update(newsEntity)

    fun delete(id: String, deleted: Boolean) =
        newsDao.delete(id, deleted)

    fun updateArticle(id: String, read: Boolean) =
        newsDao.updateArticle(id, read)

    fun getAllArticles(deleted: Boolean) =
        newsDao.getAllArticles(deleted)

    suspend fun getAllArticlesFromRetrofit(): Boolean {
        val dateFormat = SimpleDateFormat(Constants.API_PROVIDING_DATE_FORMAT, Locale.US)
        val response = newsApiService.getAllArticles("in",Constants.NEWS_APP_API_KEY)
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
                            read = false, deleted = false
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