package com.example.composejoyride.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composejoyride.data.entitites.CacheArticle

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: CacheArticle)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(articleList: List<CacheArticle>)

    @Query("UPDATE articles SET articleTitle = :newTitle, articleText = :newText, articleLink = :newLink WHERE id = :id")
    suspend fun update(id: Int, newTitle: String, newText: String, newLink: String)

    @Query("SELECT * from articles")
    suspend fun getAllItems(): List<CacheArticle>

    @Query("DELETE FROM articles")
    suspend fun deleteAll()

    @Query("SELECT id from articles WHERE articleLink = :url")
    suspend fun getIdByLink(url: String): Int

    @Query("SELECT * from articles WHERE id = :id")
    suspend fun getArticle(id: Int): CacheArticle

    @Query("SELECT id from articles")
    suspend fun getIds(): List<Int>
}