package com.example.newsapp.api

import com.example.newsapp.data.NewsApi
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("latest?apikey=pub_65115e2d5d2b7b7b11e8ff66536884c984bbb")
    suspend fun getArticle(): NewsApi

}