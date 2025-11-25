package com.example.newsdemo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.newsdemo.model.NewsItem
import com.example.newsdemo.ui.components.NewsItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    newsList: List<NewsItem>,
    isRefreshing: Boolean,      // 新增：是否正在刷新
    onRefresh: () -> Unit,      // 新增：刷新的回调
    onNewsClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("头条新闻") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        // PullToRefreshBox 是 Material3 的下拉刷新容器
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
            ) {
                items(newsList) { news ->
                    NewsItemView(
                        news = news,
                        onClick = { onNewsClick(news.url) }
                    )
                }
            }
        }
    }
}