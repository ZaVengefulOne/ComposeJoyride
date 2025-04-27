package com.example.composejoyride.data.repositories

import com.example.composejoyride.data.repositories.interfaces.IRhymeRepository
import com.example.composejoyride.data.utils.Constants
import org.jsoup.Jsoup

class RhymeRepository : IRhymeRepository {
    override suspend fun getRhymes(input: String, stress: Int): List<String> {
        val document =
            Jsoup.connect(Constants.BASE_RHYMES_URL + input + "/${stress}")
                .get()
        val rhyme = document.getElementsByClass("riLi")
        return rhyme.map { it.text().toString() }
    }
}