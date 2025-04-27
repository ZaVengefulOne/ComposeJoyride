package com.example.composejoyride.di.modules

import android.content.Context
import androidx.room.Room
import com.example.composejoyride.data.dao.NotesDao
import com.example.composejoyride.data.databases.NotesDatabase
import com.example.composejoyride.data.repositories.NotesRepository
import com.example.composejoyride.data.repositories.RhymeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase{
        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "notes"
        ).build()
    }

    @Provides
    fun provideNotesDao(database: NotesDatabase): NotesDao{
        return database.notesDao()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository {
        return NotesRepository(notesDao)
    }

    @Provides
    @Singleton
    fun provideRhymeRepository(): RhymeRepository{
        return RhymeRepository()
    }

//    @Provides
//    @Singleton
//    fun provideApplication(@ApplicationContext context: Context): Application {
//        return context as Application
//    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}
