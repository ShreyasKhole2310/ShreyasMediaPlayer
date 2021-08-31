package com.example.shreyasencoratest.model

import com.example.shreyasencoratest.api.FetchSongsInterface
import com.example.shreyasencoratest.data.DataSource

class SongListRepo(private val songsDataSource: DataSource) {
    suspend fun getTopSongs() = songsDataSource.getTopSongs()
    suspend fun getSongsfromDB() = songsDataSource.getTopSongsFromDB()
}