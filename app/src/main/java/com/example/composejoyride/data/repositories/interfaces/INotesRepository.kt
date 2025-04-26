package com.example.composejoyride.data.repositories.interfaces

import com.example.composejoyride.data.entitites.Note

interface INotesRepository {
  //  val allNotes: List<Note>
//    val searchResults: MutableLiveData<Note>
    suspend fun insertNote(newNote: Note)
    suspend fun getNotes(): List<Note>
    suspend fun updateNote(id: Int, newName: String, newText: String)
    suspend fun findNote(name: String): Note?
    suspend fun deleteNote(name: String)
    suspend fun deleteAll()
    suspend fun asyncFind(name: String):Note?
}