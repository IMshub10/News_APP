package com.summer.newsapp.data.room.repository

import android.app.Application
import com.summer.newsapp.data.room.dao.NewsDao
import com.summer.newsapp.data.room.database.NewsDatabase
import com.summer.newsapp.data.room.model.NewsEntity

class NewsDBRepository(application: Application) {
    private val newsDao: NewsDao

    init {
        val newsDatabase = NewsDatabase.getInstance(application)
        newsDao = newsDatabase.getNewsDao()
    }

    suspend fun ignoredInsert(newsEntity: NewsEntity) =
        newsDao.insert(newsEntity)

    suspend fun update(newsEntity: NewsEntity) =
        newsDao.update(newsEntity)

    suspend fun delete(id: String) =
        newsDao.delete(id)

    fun getAllNews() =
        newsDao.getAllNews()
}