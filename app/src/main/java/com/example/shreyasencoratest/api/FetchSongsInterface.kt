package com.example.shreyasencoratest.api

import com.example.shreyasencoratest.beanclasses.Feed
import com.example.shreyasencoratest.database.SongEntity

interface FetchSongsInterface {
    suspend fun getTopSongs(): ApiResultStatus<Feed>
    suspend fun getTopSongsFromDB(): List<SongEntity>
}