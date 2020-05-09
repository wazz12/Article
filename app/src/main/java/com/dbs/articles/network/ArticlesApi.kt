package com.dbs.articles.network

import com.dbs.articles.model.Article
import com.dbs.articles.model.ArticleDetail
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticlesApi {

    @GET("article")
    fun getAllArticlesAsync(): Deferred<Response<List<Article>>>

    @GET("article/{id}")
    fun getArticleDetailAsync(@Path("id") id: Int): Deferred<Response<ArticleDetail>>
}