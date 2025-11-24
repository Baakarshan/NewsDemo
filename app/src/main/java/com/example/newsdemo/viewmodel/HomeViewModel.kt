package com.example.newsdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsdemo.network.NetworkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    // 1. 新增：专门控制下拉刷新状态的流 (true=正在刷, false=刷完了)
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        getNews(isInit = true)
    }

    // 修改点：增加 isInit 参数，区分是“第一次进App”还是“手动下拉”
    fun getNews(isInit: Boolean = false) {
        viewModelScope.launch {
            if (isInit) {
                _uiState.value = NewsUiState.Loading // 第一次进，显示全屏大圈圈
            } else {
                _isRefreshing.value = true // 下拉刷新，显示顶部小圈圈
            }

            try {
                // 这里填你的 API KEY
                val response = NetworkManager.api.getNews(apiKey = "bdc76391c06e53538de55d16c5ec085b", num = 10)

                if (response.code == 200) {
                    val newsList = response.result?.newsList ?: emptyList()
                    _uiState.value = NewsUiState.Success(newsList)
                } else {
                    _uiState.value = NewsUiState.Error("API 错误: ${response.msg}")
                }

            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error("网络请求失败: ${e.message}")
            } finally {
                // 无论成功失败，最后都要把“正在刷新”的圈圈关掉
                _isRefreshing.value = false
            }
        }
    }
}