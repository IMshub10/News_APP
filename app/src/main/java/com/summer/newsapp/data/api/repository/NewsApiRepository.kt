package com.summer.newsapp.data.api.repository

import android.util.Log
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import com.summer.newsapp.data.room.model.NewsEntity
import com.summer.newsapp.data.room.repository.NewsDBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class NewsApiRepository(
    private val dbRepository: NewsDBRepository
) {
    private val tag = "NewsApiRepository"

    fun updateNewsInLocalDB(newsApiClient: NewsApiClient, format: SimpleDateFormat) {
        newsApiClient.getTopHeadlines(
            TopHeadlinesRequest.Builder()
                .country("in")
                .pageSize(50)
                .build(),
            object : NewsApiClient.ArticlesResponseCallback {
                override fun onSuccess(response: ArticleResponse) {
                    CoroutineScope(Dispatchers.IO).launch {
                        for (newsArticle in response.articles) {
                            val date = format.parse(newsArticle.publishedAt.toString())
                            if (date != null) {
                                Log.e("Timestamp",date.time.toString())
                                dbRepository.ignoredInsert(
                                    NewsEntity(
                                        date.time.toString(),
                                        newsArticle.title.orEmpty(),
                                        newsArticle.description.orEmpty(),
                                        newsArticle.urlToImage.orEmpty(),
                                        newsArticle.publishedAt.orEmpty(),
                                        date.time
                                    )
                                )
                            }
                        }
                    }
                }
                override fun onFailure(throwable: Throwable) {
                    Log.e(tag, throwable.message.toString())
                }
            }
        )
    }
}