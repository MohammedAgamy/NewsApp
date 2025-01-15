package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.data.Result
import com.example.newsapp.model.SliderModel
import com.example.newsapp.ui.ui.theme.NewsAppTheme

class ArticleActivity : ComponentActivity() {
    val viewModel: SliderModel by viewModels() // Replace with your ViewModel setup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val articleId = intent.getStringExtra("articleId")
        if (articleId == null) {
            finish() // Exit if no articleId is provided
            return
        }

        setContent {
            NewsAppTheme {
                ArticleDetailScreen(viewModel, articleId)

            }
        }
    }
}

@Composable
fun ArticleDetailScreen(viewModel: SliderModel, articleId: String) {
    val article by viewModel.getArticleById(articleId).collectAsState(initial = null)


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (article == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Move Log statements inside the else block
            Log.d("TAGID2", "Article ID: $articleId")
            Log.d("TAGID2", "Article Content: ${article!!.content}")
            ArticleDetailContent(article!!)

        }

    }
}

@Composable
fun ArticleDetailContent(article: Result) {
    Box {
        // Article Image
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = rememberAsyncImagePainter(model = article.image_url),
            contentDescription = "Image for ${article.title}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(450.dp)
        )


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(450.dp), // Position it at the bottom center,
            shape = RoundedCornerShape(22.dp), // Rounded corners
            elevation = CardDefaults.elevatedCardElevation(4.dp) // Shadow

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(4.dp)
            ) {
                Text(
                    text = article.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Article Content
                Text(
                    text = article.description,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

        }

    }
}
