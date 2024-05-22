package com.example.composejoyride.ui.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composejoyride.data.databases.NotesDatabase
import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.repositories.NotesRepository

class NotesViewModel(application: Application) : ViewModel() {
    val allNotes: LiveData<List<Note>>
    private val repository : NotesRepository
    val searchResults: MutableLiveData<List<Note>>
    init {
        val noteDb = NotesDatabase.getDatabase(application)
        val notesDao = noteDb.notesDao()
        repository = NotesRepository(notesDao)

        allNotes = repository.allNotes
        searchResults = repository.searchResults
    }

    fun insertNote(note: Note){
        repository.insertNote(note)
    }

    fun findNote(name: String){
        repository.findNote(name)
    }

    fun deleteNote(name: String){
        repository.deleteNote(name)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

    fun updateNote(id: Int, newName: String, newText: String){
        repository.updateNote(id, newName,newText)
    }
}