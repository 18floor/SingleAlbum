package ru.netology.singlealbum.repository

import androidx.lifecycle.*
import okio.IOException
import ru.netology.singlealbum.api.AlbumApi
import ru.netology.singlealbum.db.TrackDao
import ru.netology.singlealbum.db.TrackEntity
import ru.netology.singlealbum.db.toDto
import ru.netology.singlealbum.db.toEntity
import ru.netology.singlealbum.dto.Album
import ru.netology.singlealbum.model.ApiError
import ru.netology.singlealbum.model.NetworkError
import ru.netology.singlealbum.model.UnknownError

class AlbumRepositoryImpl(private val dao: TrackDao) : AlbumRepository {

    override suspend fun getAlbum(): Album {
        try {
            val response = AlbumApi.apiService.getAlbum()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return response.body() ?: throw  ApiError(response.code(), response.message())

        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw  UnknownError
        }
    }

    override suspend fun insertTracks() {
        try {
            dao.insertTracks(getAlbum().tracks.toEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw  UnknownError
        }
    }

    override suspend fun isPlayed(id: Int) {
        try {
            dao.isPlayed(id)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw  UnknownError
        }
    }

    override val data = dao.getTracks().map(List<TrackEntity>::toDto)

}