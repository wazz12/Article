package com.dbs.articles.network

import com.dbs.articles.model.Article
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ArticlesApi {

    @GET("article")
    fun getAllArticlesAsync(): Deferred<Response<List<Article>>>
}