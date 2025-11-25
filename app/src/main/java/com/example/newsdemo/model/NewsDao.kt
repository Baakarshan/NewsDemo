package com.example.newsdemo.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    // 1. 查：获取所有新闻
    // 返回 Flow<List<NewsItem>>，这意味着只要数据库变了，UI 会自动收到通知！
    @Query("SELECT * FROM news_table ORDER BY time DESC")
    fun getAllNews(): Flow<List<NewsItem>>

    // 2. 增/改：插入一堆新闻
    // OnConflictStrategy.REPLACE 表示：如果 ID 重复了，就用新的覆盖旧的
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsList: List<NewsItem>)

    // 3. 删：清空所有缓存（可选，比如下拉刷新时想先清空再存）
    @Query("DELETE FROM news_table")
    suspend fun deleteAll()
}