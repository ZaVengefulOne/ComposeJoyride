package com.example.composejoyride.data.repositories.interfaces

interface IRhymeRepository {
    suspend fun getRhymes(input: String, stress: Int): List<String>
}