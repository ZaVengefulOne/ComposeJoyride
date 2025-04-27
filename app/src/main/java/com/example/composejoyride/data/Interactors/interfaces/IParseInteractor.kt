package com.example.composejoyride.data.Interactors.interfaces

interface IParseInteractor {
    suspend fun getRhymes(input: String, stress: Int): List<String>
    suspend fun getRandomArticle(): List<String>
    suspend fun getArticles(): List<List<String>>
    suspend fun getArticle(url: String): List<String>
}