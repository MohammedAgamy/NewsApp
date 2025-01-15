package com.example.newsapp.components

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.model.SliderModel
import com.example.newsapp.ui.AllNewsActivity
import com.example.newsapp.ui.ArticleActivity

@Preview
@Composable
fun ListOfRecommendation(viewModel: SliderModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val articles by viewModel.article.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween, // Space items apart
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Recommendation",
            modifier = Modifier
                .padding(6.dp)
                .weight(1f),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 22.sp
        )
        Text(
            text = "View All ",
            modifier = Modifier
                .padding(6.dp)
                .clickable {
                    val intent = Intent(context, AllNewsActivity::class.java)
                    context.startActivity(intent)
                },
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            fontSize = 13.sp,
        )

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(articles.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .clickable {
                        // Open the article detail screen
                        val intent = Intent(context, ArticleActivity::class.java).apply {
                            putExtra("articleId", articles[index].article_id) // Pass article ID
                        }
                        Log.d("TAGID", articles[index].article_id)
                        context.startActivity(intent)
                    }

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.elevatedCardElevation()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = articles[index].image_url),
                        contentDescription = "Image for ${articles[index].title}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                }

                Text(
                    text = articles[index].title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(6.dp)
                        .padding(bottom = 4.dp)
                        .weight(3f)
                )
            }

        }
    }


}