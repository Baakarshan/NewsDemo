package com.example.newsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsdemo.model.AppDatabase
import com.example.newsdemo.ui.HomeScreen
import com.example.newsdemo.viewmodel.HomeViewModel
import com.example.newsdemo.viewmodel.NewsUiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // 1. 获取数据库实例 (单例)
            val database = AppDatabase.getDatabase(applicationContext)

            // 2. 创建仓库实例
            val repository = com.example.newsdemo.data.NewsRepository(
                newsDao = database.newsDao(),
                apiService = com.example.newsdemo.network.NetworkManager.api
            )

            // 3. 使用工厂创建 ViewModel (这是关键变化！)
            val viewModel: HomeViewModel = viewModel(
                factory = com.example.newsdemo.viewmodel.HomeViewModelFactory(repository)
            )

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