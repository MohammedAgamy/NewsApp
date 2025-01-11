package com.example.newsapp.data

data class NewsApi(
    val nextPage: String,
    val results: List<Result>,
    val status: String,
    val totalResults: Int
)