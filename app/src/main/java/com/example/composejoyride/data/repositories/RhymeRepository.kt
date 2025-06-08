package com.example.composejoyride.data.repositories

import com.example.composejoyride.data.Interactors.ParseInteractor
import com.example.composejoyride.data.repositories.interfaces.IRhymeRepository
import com.example.composejoyride.di.models.Rhyme
import javax.inject.Inject

class RhymeRepository @Inject constructor(private val interactor: ParseInteractor) :
    IRhymeRepository {
    override suspend fun getRhymes(input: Rhyme): List<String> {
        return interactor.getRhymes(input)
    }
}