package com.example.composejoyride.data.databases

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.composejoyride.data.dao.NotesDao
import com.example.composejoyride.data.entitites.Note

@Database(entities = [Note::class], version = 4, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {

        private var Instance: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            synchronized(this) {
                var instance = Instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "notes"
                    ).fallbackToDestructiveMigration(true)
                        .build()
                    Instance = instance
                }
                return instance
            }
        }
    }
}