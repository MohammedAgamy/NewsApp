package com.example.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.components.ListOfRecommendation
import com.example.newsapp.model.SliderModel
import com.example.newsapp.ui.ui.theme.NewsAppTheme

class AllNewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Spacer(modifier = Modifier.height(40.dp))
                ShowList()
            }
        }
    }

    @Composable
    fun ShowList() {
        val slideModel: SliderModel = viewModel(factory = factory)
        ListOfRecommendation(slideModel)
    }


}


val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SliderModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return SliderModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}