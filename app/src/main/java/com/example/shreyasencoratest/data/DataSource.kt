package com.example.shreyasencoratest.data

import com.example.shreyasencoratest.api.ApiResponseService
import com.example.shreyasencoratest.api.ApiResultStatus
import com.example.shreyasencoratest.api.FetchSongsInterface
import com.example.shreyasencoratest.api.RetrofitObject
import com.example.shreyasencoratest.beanclasses.Feed
import com.example.shreyasencoratest.database.SongEntity
import com.example.shreyasencoratest.database.SongRoomDatabase

class DataSource(val appDatabase: SongRoomDatabase): FetchSongsInterface {
    private var rssFeedService: ApiResponseService = RetrofitObject.feedService

    override suspend fun getTopSongs(): ApiResultStatus<Feed> {
        return try {
            val response = rssFeedService.getTopSongs()
            ApiResultStatus(ApiResultStatus.Status.SUCCESS, response, "")
        } catch (e: Exception) {
            ApiResultStatus(ApiResultStatus.Status.ERROR, null, e.message)
        }
    }

    override suspend fun getTopSongsFromDB(): List<SongEntity> {
        return appDatabase.songDao().getAllSongs()
    }
}