package com.example.newsdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsdemo.network.NetworkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import com.example.newsdemo.data.NewsRepository

class HomeViewModel(private val repository: NewsRepository) : ViewModel() {

    // 以前这里默认是 Loading，现在我们可以给个 Success(empty) 或者 Loading 都可以
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        // 1. 启动观察者：死死盯着数据库
        viewModelScope.launch {
            // collect 会一直挂起，监听数据库的变化
            repository.allNewsStream.collect { cachedNews ->
                if (cachedNews.isNotEmpty()) {
                    // 只要数据库里有东西，立马显示出来！(实现了离线查看)
                    _uiState.value = NewsUiState.Success(cachedNews)
                } else {
                    // 数据库是空的，可能是第一次安装，保持 Loading
                    _uiState.value = NewsUiState.Loading
                }
            }
        }

        // 2. 只有第一次启动时，才自动触发一次网络请求
        getNews(isInit = true)
    }

    fun getNews(isInit: Boolean = false) {
        viewModelScope.launch {
            if (!isInit) _isRefreshing.value = true

            try {
                // 指挥仓库去刷新数据
                // 注意：这里不用处理 onSuccess，因为一旦 Repository 写入数据库，
                // 上面的 collect 就会自动收到通知并更新 UI。这就是单一数据源的魅力！
                repository.refreshNews("bdc76391c06e53538de55d16c5ec085b") // <--- 记得填Key

            } catch (e: Exception) {
                // 只有网络报错时，我们才手动通知 UI 显示错误提示
                // (而且只在数据库本来就没数据的时候显示错误页，有数据就弹个Toast其实更好，这里先简化)
                if (_uiState.value is NewsUiState.Loading) {
                    _uiState.value = NewsUiState.Error("加载失败: ${e.message}")
                }
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}

// 这是一个通用的样板代码，专门用来创建带参数的 ViewModel
class HomeViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}