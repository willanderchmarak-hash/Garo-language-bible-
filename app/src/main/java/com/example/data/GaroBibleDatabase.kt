package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Book::class, Verse::class, Bookmark::class, Devotional::class],
    version = 1,
    exportSchema = false
)
abstract class GaroBibleDatabase : RoomDatabase() {
    abstract fun bibleDao(): BibleDao

    companion object {
        @Volatile
        private var INSTANCE: GaroBibleDatabase? = null

        fun getDatabase(context: Context): GaroBibleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GaroBibleDatabase::class.java,
                    "garo_bible_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
