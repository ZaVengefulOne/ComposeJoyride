package com.example.composejoyride.data.Interactors.interfaces

interface IParseInteractor {
    suspend fun getRhymes(input: String, stress: Int): List<String>
    suspend fun getRandomTopic(): List<String>
    suspend fun getTopics(): List<List<String>>
}