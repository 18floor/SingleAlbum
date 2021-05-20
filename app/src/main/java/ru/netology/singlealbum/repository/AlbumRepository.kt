package ru.netology.singlealbum.repository

import androidx.lifecycle.LiveData
import ru.netology.singlealbum.dto.Album
import ru.netology.singlealbum.dto.Track

interface AlbumRepository {
    val data: LiveData<List<Track>>
    suspend fun getAlbum(): Album
    suspend fun insertTracks()
    suspend fun isPlayed(id: Int)
}