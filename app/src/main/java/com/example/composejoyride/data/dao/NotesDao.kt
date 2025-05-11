package com.example.composejoyride.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composejoyride.data.entitites.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Query("UPDATE notes SET note_name = :newName, note_content_html = :newText WHERE id = :id")
    suspend fun update(id: Int, newName: String, newText: String)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * from notes WHERE id = :id LIMIT 1")
    suspend fun getItem(id: Int): Note?

    @Query("SELECT id from notes WHERE note_name = :name LIMIT 1")
    suspend fun getIdByName(name: String): Int

    @Query("SELECT * from notes ORDER BY id DESC")
    fun getAllItems(): Flow<List<Note>>

    @Query("DELETE FROM notes")
    suspend fun deleteAll()
}