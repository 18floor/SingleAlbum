package ru.netology.singlealbum.model

import ru.netology.singlealbum.dto.Album
import ru.netology.singlealbum.utils.Empty

data class AlbumModel(
    val album: Album = Empty.emptyAlbum,
    val loading: Boolean = false,
    val error: Boolean = false,
)
