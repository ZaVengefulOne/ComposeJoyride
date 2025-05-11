package com.example.composejoyride.data.repositories.interfaces

import com.example.composejoyride.data.entitites.Note
import kotlinx.coroutines.flow.Flow

interface INotesRepository {
    val allNotes: Flow<List<Note>>
//    val searchResults: MutableLiveData<Note>
    suspend fun insertNote(newNote: Note): Long
    //suspend fun getNotes(): List<Note>
    suspend fun updateNote(id: Int, newName: String, newText: String)
    suspend fun findNote(id: Int): Note?
    suspend fun getNoteId(name: String): Int
    suspend fun deleteNote(id: Int)
    suspend fun deleteAll()
}