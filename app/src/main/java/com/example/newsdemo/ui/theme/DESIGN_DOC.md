# NewsDemo 技术架构设计文档

## 1. 项目概述

本项目旨在开发一款基于 Android Jetpack Compose 的新闻阅读 App。
核心目标是练习 MVVM 架构、网络请求 (Retrofit) 以及 UI 构建。

## 2. 技术栈

- **语言**: Kotlin
- **UI 框架**: Jetpack Compose
- **网络库**: Retrofit + OkHttp
- **图片加载**: Coil
- **架构模式**: MVVM (Model-View-ViewModel)

## 3. 接口定义 (API Definition)

**数据源**: 天行数据/聚合数据 (请确认你的来源)

### 3.1 新闻列表接口

- **URL**: 【 这里填你的 API 接口地址，例如 http://api.tianapi.com/topnews/index 】
- **Method**: GET
- **Key 参数**: key = 【 填你的 API Key (注意代码里不要直接明文提交，这里只是文档记录) 】
- **其他参数**: 【 例如 page=1, page_size=10 】

### 3.2 响应数据分析 (Response JSON)

我们主要关注以下字段（请根据你刚才在浏览器里测试拿到的 JSON 填写）：

- **Code 字段**: 【 例如 code 或 error_code，代表成功是 200 】
- **List 字段**: 【 那个包含新闻数组的字段名，例如 newslist 或 result.data 】
- **新闻标题**: 【 字段名，例如 title 】
- **图片地址**: 【 字段名，例如 picUrl 或 thumbnail_pic_s 】
- **来源/时间**: 【 字段名，例如 description 或 date 】

## 4. 模块划分

- `model`: 数据模型 (NewsResponse, NewsItem)
- `network`: 网络请求 (NewsApiService, RetrofitClient)
- `viewmodel`: 状态管理 (HomeViewModel)
- `ui`: 界面层 (HomeScreen, NewsCard)