package ru.netology.singlealbum.utils

import ru.netology.singlealbum.dto.Album
import ru.netology.singlealbum.dto.Track

object Empty {
    val emptyAlbum = Album(
        id = 0,
        title = "",
        subtitle = "",
        artist = "",
        published = "",
        genre = "",
        tracks = emptyList()
    )

    val emptyTracks = Track(
        id = 0,
        file = "",
        isPlayed = false,
    )
}