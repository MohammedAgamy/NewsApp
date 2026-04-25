package com.agamy.newsapp.data.repository

import com.agamy.newsapp.data.model.NewsModel
import com.agamy.newsapp.data.model.NewsModelItem

class NewsRepository(private val apiService: ApiService) {

    suspend fun getNews(): Result<NewsModel>{
        return try {
            Result.success(apiService.getNews()) // NewsModel ✅

        }catch (e: Exception){
            Result.failure(e)
        }
    }
}