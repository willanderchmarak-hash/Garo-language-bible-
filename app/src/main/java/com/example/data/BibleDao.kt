package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BibleDao {
    @Query("SELECT * FROM books ORDER BY id ASC")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    suspend fun getBookById(id: Int): Book?

    @Query("SELECT * FROM verses WHERE bookId = :bookId AND chapterNumber = :chapterNumber ORDER BY verseNumber ASC")
    fun getVerses(bookId: Int, chapterNumber: Int): Flow<List<Verse>>

    @Query("SELECT * FROM verses WHERE text LIKE '%' || :query || '%' LIMIT 100")
    fun searchVerses(query: String): Flow<List<Verse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<Book>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerse(verse: Verse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerses(verses: List<Verse>)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE bookId = :bookId AND chapterNumber = :chapterNumber AND verseNumber = :verseNumber)")
    fun isBookmarked(bookId: Int, chapterNumber: Int, verseNumber: Int): Flow<Boolean>

    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks WHERE bookId = :bookId AND chapterNumber = :chapterNumber AND verseNumber = :verseNumber")
    suspend fun deleteBookmark(bookId: Int, chapterNumber: Int, verseNumber: Int)

    @Query("SELECT * FROM devotionals WHERE date = :date LIMIT 1")
    fun getDevotionalForDate(date: String): Flow<Devotional?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevotional(devotional: Devotional)

    @Query("SELECT COUNT(*) FROM verses")
    suspend fun getVerseCount(): Int
}
