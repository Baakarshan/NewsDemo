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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 1. 获取 ViewModel 实例
            // viewModel() 函数会自动帮你创建或者获取现有的 HomeViewModel
            val viewModel: HomeViewModel = viewModel()

            // 2. 观察状态 (StateFlow -> Compose State)
            // 只要 viewModel 里的 _uiState 变了，这里就会自动刷新
            val uiState by viewModel.uiState.collectAsState()

            // 3. 根据不同的状态，画不同的界面
            when (val state = uiState) {
                is NewsUiState.Loading -> {
                    // 状态是加载中：显示一个转圈圈
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is NewsUiState.Success -> {
                    // 状态是成功：显示昨天的 HomeScreen，并把数据传给它
                    HomeScreen(newsList = state.news)
                }
                is NewsUiState.Error -> {
                    // 状态是失败：显示错误文字
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message)
                    }
                }
            }
        }
    }
}