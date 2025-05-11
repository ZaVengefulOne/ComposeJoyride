package com.example.composejoyride.data.repositories

import com.example.composejoyride.data.dao.NotesDao
import com.example.composejoyride.data.entitites.Note
import com.example.composejoyride.data.repositories.interfaces.INotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesRepository(private val notesDao: NotesDao): INotesRepository {

    override val allNotes: Flow<List<Note>> = notesDao.getAllItems()


//    override suspend fun getNotes(): List<Note> {
//        return notesDao.getAllItems()
//    }


    override suspend fun insertNote(newNote: Note): Long{
            return notesDao.insert(newNote)
    }

    override suspend fun updateNote(id: Int, newName: String, newText: String) {
            notesDao.update(id, newName, newText)
    }


    override suspend fun deleteNote(id: Int) {
            notesDao.delete(id)
    }

    override suspend fun getNoteId(name: String): Int {
        return notesDao.getIdByName(name)
    }

    override suspend fun findNote(id: Int): Note? {
            return notesDao.getItem(id)
    }

    override suspend fun deleteAll(){
            notesDao.deleteAll()
    }

}