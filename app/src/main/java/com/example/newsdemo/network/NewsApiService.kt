package com.example.newsdemo.network

import com.example.newsdemo.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    // 这里的地址对应：https://apis.tianapi.com/social/index
    // Retrofit 会自动把 BaseURL 和这里的路径拼起来
    @GET("social/index")
    suspend fun getNews(
        @Query("key") apiKey: String, // 你的 API Key
        @Query("num") num: Int = 10   // 每次拿几条，默认10条
    ): NewsResponse
}