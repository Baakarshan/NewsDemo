package com.example.newsdemo.data

import android.util.Log
import com.example.newsdemo.model.NewsDao
import com.example.newsdemo.model.NewsItem
import com.example.newsdemo.network.NewsApiService
import kotlinx.coroutines.flow.Flow

// 仓库类：手里拿着 Dao (管本地) 和 Api (管网络)
class NewsRepository(
    private val newsDao: NewsDao,
    private val apiService: NewsApiService
) {
    // 1. 提供给 ViewModel 的数据流
    // 我们直接把数据库里的数据暴露出去，UI 只要观察这个流，数据库一变，UI 就变
    val allNewsStream: Flow<List<NewsItem>> = newsDao.getAllNews()

    // 2. 刷新数据逻辑
    suspend fun refreshNews(apiKey: String) {
        try {
            // A. 先去网络请求最新的
            val response = apiService.getNews(apiKey = "bdc76391c06e53538de55d16c5ec085b", num = 10)

            if (response.code == 200) {
                val newsList = response.result?.newsList ?: emptyList()
                if (newsList.isNotEmpty()) {
                    // B. 请求到了，赶紧存进数据库
                    // 注意：我们在 Dao 里写了 OnConflictStrategy.REPLACE，所以旧的会被覆盖
                    Log.d("NewsRepository", "网络请求成功，正在写入数据库... ${newsList.size} 条")
                    newsDao.insertAll(newsList)
                }
            } else {
                throw Exception("API Error: ${response.msg}")
            }
        } catch (e: Exception) {
            // 如果没网，这里会报错。
            // 但因为我们 UI 观察的是数据库，所以即使网络报错，UI 依然能显示旧数据！
            // 这里我们可以选择把错误抛出去给 ViewModel 处理
            Log.e("NewsRepository", "刷新失败: ${e.message}")
            throw e
        }
    }
}