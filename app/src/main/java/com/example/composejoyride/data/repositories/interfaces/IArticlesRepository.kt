package com.example.composejoyride.data.repositories.interfaces

interface IArticlesRepository {
    suspend fun getRandomArticle(): List<String>
    suspend fun getArticles(): List<List<String>>
    suspend fun getArticle(url: String): List<String>
}