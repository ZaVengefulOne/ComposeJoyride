package com.example.composejoyride.data.repositories

import com.example.composejoyride.data.dao.NotesDao
import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.repositories.interfaces.INotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesRepository(private val notesDao: NotesDao): INotesRepository {

//    override val allNotes: List<Note> = notesDao.getAllItems()
//    override val searchResults: MutableLiveData<Note> =
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    override suspend fun getNotes(): List<Note> {
        return notesDao.getAllItems()
    }


    override suspend fun insertNote(newNote: Note){
        coroutineScope.launch (Dispatchers.IO){
            notesDao.insert(newNote)
        }
    }

    override suspend fun updateNote(id: Int, newName: String, newText: String) {
        coroutineScope.launch(Dispatchers.IO) {
            notesDao.update(id, newName, newText)
        }
    }

    override suspend fun deleteNote(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            notesDao.delete(name)
        }
    }

    override suspend fun findNote(name: String): Note? {
            return asyncFind(name)
    }

    override suspend fun deleteAll(){
            notesDao.deleteAll()
    }

    override suspend fun asyncFind(name: String): Note? {
        return notesDao.getItem(name)
    }
}