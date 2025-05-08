package com.example.composejoyride.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.composejoyride.data.dao.ArticlesDao
import com.example.composejoyride.data.entitites.CacheArticle

@Database(entities = [CacheArticle::class], version = 1, exportSchema = false)
abstract class ArticlesDatabase: RoomDatabase()  {
    abstract fun articlesDao(): ArticlesDao

    companion object{

        private var Instance: ArticlesDatabase? = null

        fun getDatabase(context: Context): ArticlesDatabase {
            synchronized(this){
                var instance = Instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ArticlesDatabase::class.java,
                        "articles"
                    ).fallbackToDestructiveMigration()
                        .build()
                    Instance = instance
                }
                return instance
            }
        }
    }
}