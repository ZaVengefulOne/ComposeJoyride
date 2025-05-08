package com.example.composejoyride.data.repositories.interfaces

import com.example.composejoyride.data.entitites.CacheArticle
import com.example.composejoyride.di.models.Article

interface IArticlesRepository {
    suspend fun getRandomArticle(): Article
    suspend fun getArticles(): List<Article>
    suspend fun getArticle(url: String): Article

    suspend fun getRandomSavedArticle(): CacheArticle
    suspend fun getSavedArticles(): List<CacheArticle>
    suspend fun getSavedArticle(url: String): CacheArticle
    suspend fun cacheArticles(articles: List<CacheArticle>)
    suspend fun clearCache()
}