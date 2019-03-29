package com.ahmedbenfadhel.henripotier.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmedbenfadhel.henripotier.model.Book

/**
 * Database to store all books
 */
@Database(
    entities = [Book::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {

        @Volatile
        private var INSTANCE: BooksDatabase? = null

        fun getInstance(context: Context): BooksDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                BooksDatabase::class.java, "books.db")
                .build()
    }
}