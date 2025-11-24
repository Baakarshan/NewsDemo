package com.example.newsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState // 关键：把 Flow 变成 State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel // 关键：获取 ViewModel
import com.example.newsdemo.ui.HomeScreen
import com.example.newsdemo.viewmodel.HomeViewModel
import com.example.newsdemo.viewmodel.NewsUiState
import androidx.core.net.toUri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: HomeViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            // 获取当前的 Context（用来启动浏览器）
            val context = androidx.compose.ui.platform.LocalContext.current

            when (val state = uiState) {
                is NewsUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is NewsUiState.Success -> {
                    // 1. 获取刷新状态
                    val isRefreshing by viewModel.isRefreshing.collectAsState()

                    HomeScreen(
                        newsList = state.news,
                        isRefreshing = isRefreshing, // 传给 UI
                        onRefresh = {
                            viewModel.getNews(isInit = false) // 触发 ViewModel 刷新
                        },
                        onNewsClick = { url ->
                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
                            intent.data = url.toUri()
                            context.startActivity(intent)
                        }
                    )
                }
                is NewsUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message)
                    }
                }
            }
        }
    }
}