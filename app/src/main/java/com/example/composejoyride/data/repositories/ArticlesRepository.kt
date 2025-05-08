package com.example.composejoyride.data.repositories

import com.example.composejoyride.data.Interactors.ParseInteractor
import com.example.composejoyride.data.dao.ArticlesDao
import com.example.composejoyride.data.entitites.CacheArticle
import com.example.composejoyride.data.repositories.interfaces.IArticlesRepository
import com.example.composejoyride.di.models.Article

import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val interactor: ParseInteractor,
    private val dao: ArticlesDao
): IArticlesRepository {

    override suspend fun getRandomArticle(): Article {
        return interactor.getRandomArticle()
    }

    override suspend fun getArticles(): List<Article> {
        return interactor.getArticles()
    }

    override suspend fun getArticle(url: String): Article {
        return interactor.getArticle(url)
    }

    override suspend fun getRandomSavedArticle(): CacheArticle {
        val ids = dao.getIds()
        return dao.getArticle(ids.random())
    }

    override suspend fun getSavedArticles(): List<CacheArticle> {
        return dao.getAllItems()
    }

    override suspend fun getSavedArticle(url: String): CacheArticle {
        val id = dao.getIdByLink(url)
        return dao.getArticle(id)
    }

    override suspend fun cacheArticles(articles: List<CacheArticle>) {
        dao.insertAll(articles)
    }

    override suspend fun clearCache() {
        dao.deleteAll()
    }
}