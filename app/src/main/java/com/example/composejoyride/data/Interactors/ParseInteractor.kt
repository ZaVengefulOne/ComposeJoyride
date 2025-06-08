package com.example.composejoyride.data.Interactors

import com.example.composejoyride.data.Interactors.interfaces.IParseInteractor
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.di.models.Article
import com.example.composejoyride.di.models.Rhyme
import org.jsoup.Jsoup

class ParseInteractor : IParseInteractor {
    override suspend fun getRhymes(input: Rhyme): List<String> {
        val document =
            Jsoup.connect(Constants.BASE_RHYMES_URL + input.text + "/${input.stress}")
                .get()
        val rhyme = document.getElementsByClass("riLi")
        return rhyme.map { it.text().toString() }
    }

    private fun getLinks(): List<String> {
        val document =
            Jsoup.connect(Constants.BASE_ARTICLES_URL)
                .get()
        val links = document.select("h3 > a")
        return links.map { it.attr("href").toString() }.dropLast(1)
    }

    override suspend fun getRandomArticle(): Article {
        val linksList = getLinks()
        val randomLink = linksList.random()
        val document =
            Jsoup.connect(randomLink)
                .get()
        val topicTitle = document.title()
        val topicText = document.select("article").text()
        return Article(topicTitle, topicText, randomLink)
    }

    override suspend fun getArticles(): List<Article> {
        val document =
            Jsoup.connect(Constants.BASE_ARTICLES_URL)
                .get()
        val articles = document.select("h3")
        val links = document.select("h3 > a")
        val articleList = articles.map { it.text().toString() }.dropLast(1)
        val articleLinks = links.map { it.attr("href").toString() }.dropLast(1)
        return articleList.zip(articleLinks) { topic, link -> Article(topic, null, link) }
    }

    override suspend fun getArticle(url: String): Article {
        val document = Jsoup.connect(url).get()
        val articleTitle = document.title()
        val articleText = document.select("article").text()
        return Article(articleTitle, articleText, url)
    }


}