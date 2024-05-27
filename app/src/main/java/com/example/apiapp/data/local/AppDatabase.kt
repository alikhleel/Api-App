package com.example.apiapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apiapp.data.local.dao.MovieDao
import com.example.apiapp.data.local.entity.ResultsEntity

@Database(entities = [ResultsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context, AppDatabase::class.java, "movie-compose-db"
                ).build()

                INSTANCE = instance
                instance
            }

        }
    }
}