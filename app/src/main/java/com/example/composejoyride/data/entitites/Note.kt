package com.example.composejoyride.data.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var note_name: String = "",
    var note_content_html: String = ""
    )