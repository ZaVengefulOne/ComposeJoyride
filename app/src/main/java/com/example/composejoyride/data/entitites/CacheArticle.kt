package com.example.composejoyride.data.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class CacheArticle(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var articleTitle: String,
    var articleText: String,
    val articleLink: String
)
