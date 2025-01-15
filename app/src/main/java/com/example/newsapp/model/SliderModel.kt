package com.example.newsapp.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.data.NewsApi
import com.example.newsapp.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


class SliderModel : ViewModel() {
    private val _sliderArticle = MutableStateFlow<List<Result>>(emptyList())
    val article: StateFlow<List<Result>> = _sliderArticle

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchSlider()

    }

    private fun fetchSlider() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = RetrofitInstance.apiService.getArticle()
                _sliderArticle.value =
                    response.results // Assuming `articles` is a list in `Article`
                Log.d("TAGModel", "Fetched Articles: ${response.results}")
            } catch (e: Exception) {
                Log.e("TAGModel", "Error fetching articles: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getArticleById(articleId: String): Flow<Result?> {
        Log.d("getArticleById", "Searching for articleId: $articleId")
        return flow {
            val response = RetrofitInstance.apiService.getArticle()
            val article = response.results.find { it.article_id == articleId }
            Log.d("getArticleById", "Found Article: $article")
            Log.d("getArticleById", "Found Article _Slider: ${_sliderArticle.value}")
            emit(article)
        }
    }


}


