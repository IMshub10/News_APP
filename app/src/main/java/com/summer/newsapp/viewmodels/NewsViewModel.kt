package com.summer.newsapp.viewmodels

import androidx.lifecycle.ViewModel
import com.summer.newsapp.data.repository.NewsRepository


class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    suspend fun deleteArticle(id: String, deleted: Boolean) = newsRepository.delete(id, deleted)
    suspend fun updateArticle(id: String, read: Boolean) =
        newsRepository.updateArticle(id, read)

    fun getAllArticles(deleted: Boolean) = newsRepository.getAllArticles(deleted)

    suspend fun getAllArticles(): Boolean {
        return newsRepository.getAllArticlesFromRetrofit()
    }
}