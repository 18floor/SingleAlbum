package ru.netology.singlealbum.repository

import ru.netology.singlealbum.dto.Album

interface AlbumRepository {
    suspend fun getAlbum(): Album
}