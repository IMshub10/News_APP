package com.summer.newsapp.data.api.apihelper

import com.summer.newsapp.data.api.model.Articles

interface NewsHelper {
    suspend fun getAllArticles() : Articles
}