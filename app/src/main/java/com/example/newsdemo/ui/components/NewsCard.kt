package com.example.newsdemo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsdemo.model.NewsItem

@Composable
fun NewsItemView(news: NewsItem, onClick: () -> Unit = {}) {
    // Card 是 Material Design 风格的卡片容器，自带阴影和圆角
    Card(
        modifier = Modifier
            .fillMaxWidth() // 宽度填满屏幕
            .padding(horizontal = 16.dp, vertical = 8.dp) // 外边距
            .height(100.dp), // 固定高度，保证列表整齐
        colors = CardDefaults.cardColors(containerColor = Color.White), // 背景白色
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // 微微的阴影
        onClick = onClick // 点击事件
    ) {
        // Row 是横向布局：左边文字，右边图片
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp) // 内边距
        ) {
            // 左边：文字区域 (Column 是纵向布局，上标题，下来源)
            Column(
                modifier = Modifier
                    .weight(1f) // 占据剩余空间的权重。重要！让文字占满剩下的空间
                    .padding(end = 8.dp) // 和右边的图片留点缝隙
            ) {
                // 1. 标题
                Text(
                    text = news.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2, // 最多显示2行
                    overflow = TextOverflow.Ellipsis, // 超出显示省略号...
                    modifier = Modifier.weight(1f) // 把下方的来源挤到底部
                )

                // 2. 来源和时间
                Text(
                    text = "${news.source}  ${news.time}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // 右边：图片区域
            // AsyncImage 是 Coil 库提供的，专门加载网络图片
            AsyncImage(
                model = news.coverImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop, // 裁剪模式：填满
                modifier = Modifier
                    .width(110.dp) // 固定宽度
                    .fillMaxHeight() // 高度撑满
                    .clip(RoundedCornerShape(6.dp)) // 图片也要圆角
            )
        }
    }
}

// --- 预览区 ---
// 这是 Compose 最强大的功能，不用运行 App 就能看效果
@Preview(showBackground = true)
@Composable
fun PreviewNewsItem() {
    // 造一条假数据来看看样子
    val fakeNews = NewsItem(
        id = "1",
        title = "刚刚！中国航天又传捷报，太激动人心了",
        description = "...",
        source = "央视新闻",
        coverImageUrl = "https://example.com/fake.jpg", // 预览图加载不出来是正常的，会显示空白占位
        url = "...",
        time = "10分钟前"
    )
    NewsItemView(news = fakeNews)
}