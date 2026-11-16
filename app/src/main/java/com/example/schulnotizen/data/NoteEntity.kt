package com.example.schulnotizen.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val subject: String,
    val content: String,
    val tabColorHex: String = "#0A84FF",
    val isOpenInTab: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
