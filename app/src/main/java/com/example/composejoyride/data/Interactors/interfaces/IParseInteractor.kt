package com.example.composejoyride.data.Interactors.interfaces


import com.example.composejoyride.di.models.Article
import com.example.composejoyride.di.models.Rhyme

interface IParseInteractor {
    suspend fun getRhymes(input: Rhyme): List<String>
    suspend fun getRandomArticle(): Article
    suspend fun getArticles(): List<Article>
    suspend fun getArticle(url: String): Article
}