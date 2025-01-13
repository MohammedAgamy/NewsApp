package com.example.newsapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

import com.example.newsapp.model.SliderModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

@Preview
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Slider(viewModel: SliderModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween, // Space items apart
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Breaking News ",
            modifier = Modifier
                .padding(6.dp)
                .weight(1f),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 22.sp
        )
        Text(
            text = "View All ",
            modifier = Modifier.padding(6.dp),
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            fontSize = 13.sp
        )

    }

    val articles by viewModel.article.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    val pagerState = rememberPagerState(initialPage = 0) // Start from page 0
    val coroutineScope = rememberCoroutineScope()
    // Auto-scroll logic with a guard for empty lists
    LaunchedEffect(articles) {
        if (articles.isNotEmpty()) { // Prevent divide by zero if images list is empty
            while (true) {
                delay(3000) // Delay between auto-scrolls (3 seconds)
                coroutineScope.launch {
                    val nextPage = (pagerState.currentPage + 1) % articles.size
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(250.dp)
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            // Horizontal Pager for Image Slider
            HorizontalPager(
                count = articles.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) { index ->
                Card(
                    modifier = Modifier
                        .width(320.dp)
                        .height(200.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.elevatedCardElevation()
                ) {

                    Box {
                        Image(
                            painter = rememberAsyncImagePainter(model = articles[index].image_url),
                            contentDescription = "Image $index",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),


                            )

                        Text(
                            text = articles[index].title, // Replace with the appropriate property
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(8.dp).align(Alignment.BottomCenter)
                        )

                    }


                }

            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Modern Indicator Row
        // Pager Indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = Color.Blue,
            inactiveColor = Color.Gray,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally) // Center align the indicator

        )
    }

}




