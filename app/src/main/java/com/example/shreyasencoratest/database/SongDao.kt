package com.example.shreyasencoratest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shreyasencoratest.beanclasses.Entry

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: SongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongsList(songList: List<SongEntity>)

    @Query("SELECT * FROM song")
    suspend fun getAllSongs(): List<SongEntity>

    @Query("SELECT COUNT(*) from song")
    suspend fun getSongCount(): Int
}