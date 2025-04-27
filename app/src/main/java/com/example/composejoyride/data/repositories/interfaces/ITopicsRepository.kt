package com.example.composejoyride.data.repositories.interfaces

interface ITopicsRepository {
    suspend fun getRandomTopic(): List<String>
    suspend fun getTopics(): List<List<String>>
}