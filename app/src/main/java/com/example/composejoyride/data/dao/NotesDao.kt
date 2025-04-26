package com.example.composejoyride.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composejoyride.data.entitites.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Query("UPDATE notes SET note_name = :newName, note_text = :newText WHERE id = :id")
    fun update(id: Int, newName: String, newText: String)

    @Query("DELETE FROM notes WHERE note_name = :name")
    fun delete(name: String)

    @Query("SELECT * from notes WHERE note_name = :name LIMIT 1")
    fun getItem(name: String): Note

    @Query("SELECT * from notes")
    fun getAllItems(): List<Note>

    @Query("DELETE FROM notes")
    fun deleteAll()
}