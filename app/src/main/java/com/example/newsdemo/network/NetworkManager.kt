package com.example.newsdemo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    // 基础地址，注意最后一定要有斜杠 /
    private const val BASE_URL = "https://apis.tianapi.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // 告诉它用 Gson 解析 JSON
        .build()

    // 对外公开的 API 服务实例
    val api: NewsApiService = retrofit.create(NewsApiService::class.java)
}