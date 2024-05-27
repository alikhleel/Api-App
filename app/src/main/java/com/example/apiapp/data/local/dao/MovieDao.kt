package com.example.apiapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.apiapp.data.local.entity.ResultsEntity


@Dao
interface MovieDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<ResultsEntity>)

    @Transaction
    @Query("DELETE FROM ResultsEntity WHERE page NOT IN (:pages)")
    suspend fun deleteMoviesByPages(pages: List<Int>)


    @Transaction
    @Query("SELECT * FROM ResultsEntity ORDER BY page ASC")
    suspend fun getCachedMovies(): List<ResultsEntity>
}