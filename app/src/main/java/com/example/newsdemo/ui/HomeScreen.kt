package com.example.newsdemo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.newsdemo.model.NewsItem
import com.example.newsdemo.ui.components.NewsItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(newsList: List<NewsItem>) {
    // Scaffold 是页面的脚手架，帮我们预留好标题栏、底部导航栏的位置
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("头条新闻") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red, // 模仿头条红
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        // LazyColumn 相当于 RecyclerView，专门处理长列表
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding) // 避免内容被标题栏挡住
                .background(Color(0xFFF5F5F5)) // 淡淡的灰色背景
        ) {
            // items 函数：把 List 数据源转换成一个个 UI 组件
            items(newsList) { news ->
                NewsItemView(news = news)
            }
        }
    }
}