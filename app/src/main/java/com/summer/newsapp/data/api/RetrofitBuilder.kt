package com.summer.newsapp.data.api

import com.summer.newsapp.data.api.apiservice.NewsService

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RetrofitBuilder @Inject constructor(){
    private  val BASE_URL =
        "https://newsapi.org/v2/"

    private var retrofit: Retrofit? = null

    private fun getRetrofit() =
        retrofit ?: Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


    val newsApiService: NewsService =
        getRetrofit().create(NewsService::class.java)
}