package ru.netology.singlealbum.repository

import androidx.lifecycle.map
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

//@Suppress("UNREACHABLE_CODE")
class AlbumRepositoryImpl(private val dao: TrackDao) : AlbumRepository {

    override val data = dao.getTracks().map(List<TrackEntity>::toDto)


    override suspend fun getAlbum(): Album {
        try {
            val response = AlbumApi.apiService.getAlbum()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw  ApiError(response.code(), response.message())
            return body

            dao.insertTracks(body.tracks.toEntity())

        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw  UnknownError
        }
    }

}