package com.dbs.articles.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dbs.articles.model.Article
import com.dbs.articles.model.ArticleDetail
import com.dbs.articles.network.ApiFactory
import com.dbs.articles.network.ArticleRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ArticleViewModel : ViewModel() {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val articleRepo: ArticleRepo = ArticleRepo(ApiFactory.articleApi)

    val allArticlesLiveData = MutableLiveData<List<Article>>()
    val articleDetailLiveData = MutableLiveData<ArticleDetail>()

    fun getAllArticles() {
        scope.launch {
            val allArticles = articleRepo.getAllArticles()
            allArticlesLiveData.postValue(allArticles)
        }
    }

    fun getArticleDetail(id: Int) {
        scope.launch {
            val articleDetail = articleRepo.getArticleDetail(id)
            articleDetailLiveData.postValue(articleDetail)
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}