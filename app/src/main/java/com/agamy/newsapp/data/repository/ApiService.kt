package com.agamy.newsapp.data.repository

import com.agamy.newsapp.data.model.NewsModel
import com.agamy.newsapp.data.model.NewsModelItem
import retrofit2.http.GET
//Define the API Interface
interface ApiService {

    @GET("posts")
    suspend fun getNews(): List<NewsModelItem>

}