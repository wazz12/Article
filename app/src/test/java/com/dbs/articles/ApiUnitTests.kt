package com.dbs.articles

import com.dbs.articles.network.ApiFactory
import com.dbs.articles.network.ArticleRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiUnitTests {

    private val articleRepo: ArticleRepo = ArticleRepo(ApiFactory.articleApi)
    private val size = 4
    private val articleId = 1

    @Test
    fun article_list_api() = runBlocking {
        val articleList = articleRepo.getAllArticles()
        assertNotNull(articleList)
        assertEquals(articleList?.size, size)
    }

    @Test
    fun article_details_api() = runBlocking {
        val articleDetails = articleRepo.getArticleDetail(articleId)
        assertNotNull(articleDetails)
        assertEquals(articleDetails?.id, articleId)
        assertNotNull(articleDetails?.text)
    }
}