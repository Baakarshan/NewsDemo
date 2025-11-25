package com.example.newsdemo.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// entities: 告诉数据库有哪些表
// version: 数据库版本号，如果你修改了表结构，版本号要 +1
@Database(entities = [NewsItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    // 提供 Dao 的获取方法
    abstract fun newsDao(): NewsDao

    // 单例模式：保证全 App 只有一个数据库实例，不然会很卡
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "news_database" // 手机里的数据库文件名
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}