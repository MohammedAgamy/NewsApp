package com.agamy.newsapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.agamy.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _state = MutableStateFlow<NewsState>(NewsState.Idle)
    val state: StateFlow<NewsState> = _state

    private val intentChannel = Channel<NewsIntent>(Channel.UNLIMITED)

    init {
        handelIntent() // ✅ must call this so intents are processed
    }

    // UI sends intents through here
    fun sendIntent(intent: NewsIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handelIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    NewsIntent.LoadNews -> fetchNews()
                    NewsIntent.RefreshNews -> fetchNews()
                }
            }
        }
    }

    private suspend fun fetchNews() {
        _state.value = NewsState.Loading

        repository.getNews().fold(
            onSuccess = { news ->
                _state.value = if (news.isEmpty()) {
                    NewsState.Empty
                } else {
                    NewsState.Success(news)
                }

            },
            onFailure = { error ->
                _state.value = NewsState.Error(error.message ?: "Unknown error")

            }
        )

    }
}

class NewsViewModelFactory(
    private val repository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}