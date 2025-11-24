package com.example.newsdemo.viewmodel // 如果你的包名不一样，注意保留第一行原样

import com.example.newsdemo.model.NewsItem

// sealed interface (密封接口) 是定义状态的神器
// 它限制了 UI 只能是这三种状态之一，不会出现奇奇怪怪的情况
sealed interface NewsUiState {
    object Loading : NewsUiState                            // 正在加载
    data class Success(val news: List<NewsItem>) : NewsUiState // 加载成功，肚子里装着新闻列表
    data class Error(val message: String) : NewsUiState     // 加载失败，肚子里装着错误信息
}