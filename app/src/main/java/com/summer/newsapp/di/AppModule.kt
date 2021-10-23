package com.summer.newsapp.di

import android.app.Application
import android.content.Context
import com.summer.newsapp.data.api.RetrofitBuilder
import com.summer.newsapp.data.api.apiservice.NewsService
import com.summer.newsapp.data.room.dao.NewsDao
import com.summer.newsapp.data.room.database.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewsApiService(retrofitBuilder: RetrofitBuilder): NewsService {
        return retrofitBuilder.newsApiService
    }

    @Singleton
    @Provides
    fun providesNewsDB(application: Application): NewsDatabase {
        return NewsDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun providesNewsDao(newsDB:NewsDatabase): NewsDao {
        return newsDB.getNewsDao()
    }
}