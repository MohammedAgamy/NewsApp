package com.agamy.newsapp.data.repository

import com.agamy.newsapp.data.model.NewsModel
import com.agamy.newsapp.data.model.NewsModelItem

class NewsRepository(private val apiService: ApiService) {

    suspend fun getNews(): Result<List<NewsModelItem>>{
        return try {
            val news = apiService.getNews()
            Result.success(news)

        }catch (e: Exception){
            Result.failure(e)
        }
    }
}