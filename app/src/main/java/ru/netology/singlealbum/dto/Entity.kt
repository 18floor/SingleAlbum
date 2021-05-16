package ru.netology.singlealbum.dto

sealed class AlbumItem {
    abstract val id: Int
}

data class Album(
    override val id: Int,
    val title: String,
    val subtitle: String,
    val artist: String,
    val published: String,
    val genre: String,
    val tracks: List<Track>
) : AlbumItem()

data class Track(
    override val id: Int,
    val file: String,
) : AlbumItem()