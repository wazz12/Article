package com.dbs.articles.network

import com.dbs.articles.model.Article
import com.dbs.articles.model.ArticleDetail

class ArticleRepo(private val api: ArticlesApi) : BaseRepo() {

    suspend fun getAllArticles(): List<Article>? {
        return safeApiCall(
            call = { api.getAllArticlesAsync().await() },
            errorMessage = "Error occurred while retrieving data"
        )
    }

    suspend fun getArticleDetail(id: Int): ArticleDetail? {
        return safeApiCall(
            call = { api.getArticleDetailAsync(id).await() },
            errorMessage = "Error occurred while retrieving data"
        )
    }
}