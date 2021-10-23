package com.summer.newsapp.viewmodels

import androidx.lifecycle.ViewModel
import com.summer.newsapp.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun deleteArticle(id: String, deleted: Boolean) = newsRepository.delete(id, deleted)
    fun updateArticle(id: String, read: Boolean) =
        newsRepository.updateArticle(id, read)

    fun getAllArticles(deleted: Boolean) = newsRepository.getAllArticles(deleted)

    suspend fun getAllArticles(): Boolean {
        return newsRepository.getAllArticlesFromRetrofit()
    }
}