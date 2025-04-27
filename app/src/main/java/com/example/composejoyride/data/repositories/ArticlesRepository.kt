package com.example.composejoyride.data.repositories

import com.example.composejoyride.data.Interactors.ParseInteractor
import com.example.composejoyride.data.repositories.interfaces.IArticlesRepository

import javax.inject.Inject

class ArticlesRepository @Inject constructor(private val interactor: ParseInteractor): IArticlesRepository {

    override suspend fun getRandomArticle(): List<String> {
        return interactor.getRandomArticle()
    }

    override suspend fun getArticles(): List<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticle(url: String): List<String> {
        return interactor.getArticle(url)
    }
}