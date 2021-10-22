package com.summer.newsapp.viewmodels.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.summer.newsapp.data.api.apihelper.NewsHelper
import com.summer.newsapp.data.repository.NewsRepository
import com.summer.newsapp.viewmodels.NewsViewModel
import java.lang.IllegalArgumentException

class NewsViewModelFactory(
    private val application: Application,
    private val newsHelper: NewsHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(NewsRepository(application, newsHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}