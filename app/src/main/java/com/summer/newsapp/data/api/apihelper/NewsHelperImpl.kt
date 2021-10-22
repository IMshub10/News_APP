package com.summer.newsapp.data.api.apihelper

import com.summer.newsapp.data.api.apiservice.NewsService
import com.summer.newsapp.utils.Constants

class NewsHelperImpl(private val apiService:NewsService) : NewsHelper{
    override suspend fun getAllArticles() = apiService.getAllArticles("in",Constants.NEWS_APP_API_KEY)
}