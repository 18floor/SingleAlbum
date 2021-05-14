package ru.netology.singlealbum.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import ru.netology.singlealbum.BuildConfig
import ru.netology.singlealbum.dto.Album

private const val BASE_URL = BuildConfig.BASE_URL

private val logging = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private val okhttp = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okhttp)
    .build()

interface MusicApiService {
    @GET("album.json")
    suspend fun getAlbum(): Response<Album>
}

object MusicApi {
    val apiService: MusicApiService by lazy(retrofit::create)
}