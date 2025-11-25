package com.example.newsdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
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
 * 加上 @Entity 注解，表示这不仅是网络数据，也是数据库里的一张表，表名叫 "news_table"
 */
@Entity(tableName = "news_table")
data class NewsItem(
    // @PrimaryKey: 主键，必须唯一。API返回的 id 是唯一的，正好拿来用
    // autoGenerate = false 表示我们用服务器给的ID，不自己生成
    @PrimaryKey(autoGenerate = false)
    val id: String,

    val title: String,
    val description: String,
    val source: String,
    @SerializedName("picUrl") val coverImageUrl: String?,
    val url: String,
    @SerializedName("ctime") val time: String
)