package com.example.shreyasencoratest.api

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit

import java.util.concurrent.TimeUnit

object RetrofitObject {
    const val BASE_URL = "http://ax.itunes.apple.com/"
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private var okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            TikXmlConverterFactory.create(
                TikXml.Builder()
                    .exceptionOnUnreadXml(false)
                    .addTypeConverter(String.javaClass, HtmlEscapeStringConverter())
                    .build()
            )
        )
        .build()

    val feedService: ApiResponseService
        get() {
            return retrofit.create(ApiResponseService::class.java)
        }
}