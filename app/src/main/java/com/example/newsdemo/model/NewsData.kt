package com.example.newsdemo.model

import com.google.gson.annotations.SerializedName

/**
 * 1. 最外层的响应壳子
 * 对应 JSON 的: { "code": 200, "msg": "...", "result": { ... } }
 */
data class NewsResponse(
    val code: Int,
    val msg: String,
    @SerializedName("result") val result: NewsResult? // result 可能是空的，加上 ?
)

/**
 * 2. 中间层的 Result
 * 对应 JSON 的: { "curpage": 1, "newslist": [ ... ] }
 */
data class NewsResult(
    @SerializedName("curpage") val curPage: Int,
    @SerializedName("allnum") val allNum: Int,
    @SerializedName("newslist") val newsList: List<NewsItem>?
)

/**
 * 3. 具体的单条新闻
 * 对应 JSON 的: { "title": "...", "picUrl": "..." }
 */
data class NewsItem(
    val id: String,
    val title: String,
    val description: String,
    val source: String,
    @SerializedName("picUrl") val coverImageUrl: String?, // 我们把 picUrl 重命名为更直观的 coverImageUrl
    val url: String,
    @SerializedName("ctime") val time: String
)