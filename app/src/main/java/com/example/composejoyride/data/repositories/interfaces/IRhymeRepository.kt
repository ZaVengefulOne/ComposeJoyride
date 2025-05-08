package com.example.composejoyride.data.repositories.interfaces

import com.example.composejoyride.di.models.Rhyme

interface IRhymeRepository {
    suspend fun getRhymes(input: Rhyme): List<String>
}