package com.dbs.articles.network

import com.dbs.articles.model.Article

class ArticleRepo(private val api: ArticlesApi) : BaseRepo() {

    suspend fun getAllArticles(): List<Article>? {
        return safeApiCall(
            call = { api.getAllArticlesAsync().await() },
            errorMessage = "Error occurred while retrieving data"
        )
    }
}