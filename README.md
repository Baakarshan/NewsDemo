# 📱 NewsDemo - 基于 Jetpack Compose & MVVM 的新闻客户端

> 这是一个用于学习 Android 现代架构 (Modern Android Architecture) 的练手项目。
> 实现了从网络获取新闻、本地数据库缓存、离线阅读以及下拉刷新等功能。

## 📸 项目演示 (Demo)



## 🛠 技术栈 (Tech Stack)

*   **语言**: [Kotlin](https://kotlinlang.org/) (100%)
*   **UI 框架**: [Jetpack Compose](https://developer.android.com/jetbrains/compose) - 声明式 UI
*   **架构模式**: MVVM (Model-View-ViewModel) + Repository Pattern
*   **异步处理**: Coroutines + Flow
*   **网络请求**: Retrofit2 + OkHttp3 + Gson
*   **本地存储**: Room Database (SQLite) - 实现单一数据源 (SSOT)
*   **图片加载**: Coil
*   **依赖管理**: Version Catalog (可选，如果你用了的话) / Gradle Kotlin DSL

## ✨ 核心功能 (Features)

- [√] **网络数据加载**: 对接天行数据 API，获取实时新闻。
- [√] **多状态管理**: 完美处理 Loading / Success / Error 三种 UI 状态。
- [√] **沉浸式列表**: 使用 `LazyColumn` 实现高性能列表渲染。
- [√] **离线缓存 (Offline First)**:
    - 使用 Room 数据库作为单一数据源。
    - 无网环境下优先展示数据库缓存，保证用户体验。
- [√] **交互优化**:
    - 集成 Material3 下拉刷新 (Pull-to-Refresh)。
    - 点击新闻卡片跳转系统浏览器阅读详情。

## 📂 项目结构 (Project Structure)

```text
com.example.newsdemo
├── data          # 数据仓库层 (Repository)
├── model         # 数据模型 (Entity, DAO)
├── network       # 网络请求 (Retrofit Service)
├── ui            # 界面层 (Screen, Components)
└── viewmodel     # 状态管理 (ViewModel, State)
```

🚀 如何运行 (How to Run)
- Clone 本仓库到本地。
- 在 network/NetworkManager.kt 或 viewmodel/HomeViewModel.kt 中配置你的 API Key。
- 使用 Android Studio (Hedgehog 或更新版本) 打开项目。
- Sync Gradle 并运行到模拟器/真机。

