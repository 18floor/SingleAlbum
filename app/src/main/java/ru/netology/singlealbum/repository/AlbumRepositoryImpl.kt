package ru.netology.singlealbum.repository

import okio.IOException
import ru.netology.singlealbum.api.AlbumApi
import ru.netology.singlealbum.dto.Album
import ru.netology.singlealbum.model.ApiError
import ru.netology.singlealbum.model.NetworkError
import ru.netology.singlealbum.model.UnknownError

class AlbumRepositoryImpl : AlbumRepository {

    override suspend fun getAlbum(): Album {
        try {
            val response = AlbumApi.apiService.getAlbum()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw  UnknownError
        }
    }
}