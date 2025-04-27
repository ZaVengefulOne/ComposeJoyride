package com.example.composejoyride.data.repositories

import com.example.composejoyride.data.Interactors.ParseInteractor
import com.example.composejoyride.data.repositories.interfaces.ITopicsRepository
import javax.inject.Inject

class TopicsRepository @Inject constructor(private val interactor: ParseInteractor): ITopicsRepository {

    override suspend fun getRandomTopic(): List<String> {
        return interactor.getRandomTopic()
    }

    override suspend fun getTopics(): List<List<String>> {
        TODO("Not yet implemented")
    }
}