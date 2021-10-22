package com.summer.newsapp.data.api.apiservice

import com.summer.newsapp.data.api.model.Articles
import com.summer.newsapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    suspend fun getAllArticles(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Articles
}