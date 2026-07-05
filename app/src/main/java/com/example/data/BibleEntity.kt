package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey val id: Int,
    val name: String,
    val englishName: String,
    val testament: String, // "Old" or "New"
    val totalChapters: Int
)

@Entity(tableName = "verses")
data class Verse(
    @PrimaryKey val id: String, // e.g. "bookId_chapter_verse"
    val bookId: Int,
    val bookName: String,
    val chapterNumber: Int,
    val verseNumber: Int,
    val text: String
)

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookId: Int,
    val bookName: String,
    val chapterNumber: Int,
    val verseNumber: Int,
    val verseText: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "devotionals")
data class Devotional(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, // YYYY-MM-DD
    val bookName: String,
    val chapterNumber: Int,
    val verseNumber: Int,
    val verseText: String,
    val title: String,
    val devotionalText: String,
    val prayer: String
)
