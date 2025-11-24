package com.example.newsdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsdemo.network.NetworkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // 1. 内部维护的可变状态（这就是数据源头）
    // 一开始默认状态是 Loading
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)

    // 2. 对外暴露的只读状态（UI 只能看，不能改，保证安全）
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    // init 代码块：ViewModel 一创建出来，就自动执行
    init {
        getNews()
    }

    // 核心功能：去网络拿新闻
    fun getNews() {
        // viewModelScope 是 ViewModel 自带的协程作用域
        // 页面关闭时它会自动取消请求，防内存泄漏
        viewModelScope.launch {
            // 先把状态重置为 Loading (适合下拉刷新时用)
            _uiState.value = NewsUiState.Loading

            try {
                // 发起网络请求 (你的 API Key 在 NetworkManager 或者这里传都行)
                // 记得把 key 换成你自己的！
                val response = NetworkManager.api.getNews(apiKey = "bdc76391c06e53538de55d16c5ec085b", num = 5)

                if (response.code == 200) {
                    // 请求成功，把数据包装进 Success 状态
                    // 注意：API 返回的 list 可能是空的，我们给个空列表保底
                    val newsList = response.result?.newsList ?: emptyList()
                    _uiState.value = NewsUiState.Success(newsList)
                } else {
                    // 业务失败 (比如 Key 过期)
                    _uiState.value = NewsUiState.Error("API 错误: ${response.msg}")
                }

            } catch (e: Exception) {
                // 网络炸了 (没网、超时)
                e.printStackTrace()
                _uiState.value = NewsUiState.Error("网络请求失败: ${e.message}")
            }
        }
    }
}