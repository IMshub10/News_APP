package com.summer.newsapp.viewmodels

import androidx.lifecycle.ViewModel
import com.summer.newsapp.data.repository.NewsRepository


class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    suspend fun deleteArticle(id:String) = newsRepository.delete(id)
    suspend fun updateArticle(id: String, read: Boolean) =
        newsRepository.updateArticle(id,read)
    fun getAllArticles(read:Boolean) = newsRepository.getAllArticles(read)

    suspend fun getAllArticles(): Boolean {
        return newsRepository.getAllArticlesFromRetrofit()
    }
}