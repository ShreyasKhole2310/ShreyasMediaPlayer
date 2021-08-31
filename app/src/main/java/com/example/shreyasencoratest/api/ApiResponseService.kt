package com.example.shreyasencoratest.api

import com.example.shreyasencoratest.beanclasses.Feed
import retrofit2.http.GET

public interface ApiResponseService {
    @GET("WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml")
    suspend fun getTopSongs(): Feed
}