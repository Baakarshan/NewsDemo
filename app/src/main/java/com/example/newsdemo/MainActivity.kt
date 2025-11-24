package com.example.newsdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope // 确保引入了这个
import com.example.newsdemo.network.NetworkManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- 临时测试代码 Start ---
        // 使用 lifecycleScope 启动协程
        lifecycleScope.launch {
            try {
                Log.d("NewsTest", "开始请求网络...")
                // 替换成你自己的 API Key！
                val response = NetworkManager.api.getNews(apiKey = "bdc76391c06e53538de55d16c5ec085b", num = 5)

                // 打印结果
                Log.d("NewsTest", "请求成功！Code: ${response.code}")
                response.result?.newsList?.forEach { news ->
                    Log.d("NewsTest", "获取到新闻: ${news.title}")
                }
            } catch (e: Exception) {
                Log.e("NewsTest", "请求失败: ${e.message}")
                e.printStackTrace()
            }
        }
        // --- 临时测试代码 End ---

        setContent {
            // ... 这里是你原本的 Compose 代码，不用动
        }
    }
}