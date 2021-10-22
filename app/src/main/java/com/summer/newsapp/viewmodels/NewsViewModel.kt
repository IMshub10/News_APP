package com.summer.newsapp.viewmodels

import androidx.lifecycle.ViewModel
import com.summer.newsapp.data.room.repository.NewsDBRepository
class NewsViewModel(private val newsDBRepository: NewsDBRepository) : ViewModel() {

    fun getAllNews() = newsDBRepository.getAllNews()

}