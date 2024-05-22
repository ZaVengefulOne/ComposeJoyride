package com.example.composejoyride.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composejoyride.data.dao.NotesDao
import com.example.composejoyride.data.entitites.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NotesRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllItems()
    val searchResults = MutableLiveData<List<Note>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertNote(newNote: Note){
        coroutineScope.launch (Dispatchers.IO){
            notesDao.insert(newNote)
        }
    }

    fun updateNote(id: Int, newName: String, newText: String) {
        coroutineScope.launch(Dispatchers.IO) {
            notesDao.update(id, newName, newText)
        }
    }

    fun deleteNote(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            notesDao.delete(name)
        }
    }

    fun findNote(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    fun deleteAll(){
        coroutineScope.launch(Dispatchers.IO) {
            notesDao.deleteAll()
        }
    }

    private fun asyncFind(name: String): Deferred<List<Note>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async notesDao.getItem(name)
        }
}