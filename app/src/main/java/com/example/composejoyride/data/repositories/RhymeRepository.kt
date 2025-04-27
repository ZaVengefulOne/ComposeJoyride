package com.example.composejoyride.data.repositories

import com.example.composejoyride.data.Interactors.ParseInteractor
import com.example.composejoyride.data.repositories.interfaces.IRhymeRepository
import javax.inject.Inject

class RhymeRepository @Inject constructor(private val interactor: ParseInteractor) : IRhymeRepository {
    override suspend fun getRhymes(input: String, stress: Int): List<String> {
       return interactor.getRhymes(input, stress)
    }
}