package com.example.composejoyride.data.Interactors

import com.example.composejoyride.data.Interactors.interfaces.IParseInteractor
import com.example.composejoyride.data.utils.Constants
import org.jsoup.Jsoup

class ParseInteractor: IParseInteractor {
    override suspend fun getRhymes(input: String, stress: Int): List<String> {
        val document =
            Jsoup.connect(Constants.BASE_RHYMES_URL + input + "/${stress}")
                .get()
        val rhyme = document.getElementsByClass("riLi")
        return rhyme.map { it.text().toString() }
    }

    private fun getLinks(): List<String> {
        val document =
            Jsoup.connect(Constants.BASE_ARTICLES_URL)
                        .get()
                val rhyme = document.select("h3")
                val links = document.select("h3 > a")
        return links.map {it.attr("href").toString()}.dropLast(1)
        }

    override suspend fun getRandomTopic(): List<String> {
        val linksList = getLinks()
        val document =
            Jsoup.connect(linksList.random())
                .get()
                val topicTitle = document.title()
                val topicText = document.select("article").text()
                return listOf(topicTitle, topicText)
        }

    override suspend fun getTopics(): List<List<String>> {
        TODO("Not yet implemented")
    }
}