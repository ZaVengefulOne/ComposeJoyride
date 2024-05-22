package com.example.composejoyride.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.composejoyride.data.entitites.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Query("UPDATE notes SET note_name = :newName, note_text = :newText WHERE id = :id")
    fun update(id: Int, newName: String, newText: String)

    @Query("DELETE FROM notes WHERE note_name = :name")
    fun delete(name: String)

    @Query("SELECT * from notes WHERE note_name = :name")
    fun getItem(name: String): List<Note>

    @Query("SELECT * from notes")
    fun getAllItems(): LiveData<List<Note>>

    @Query("DELETE FROM notes")
    fun deleteAll()
}