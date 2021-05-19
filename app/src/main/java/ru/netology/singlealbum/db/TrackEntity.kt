package ru.netology.singlealbum.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.singlealbum.dto.Track

@Entity
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val file: String,
    val isPlayed: Boolean = false,
) {
    fun toDto(): Track {
        return Track(
            id,
            file,
            isPlayed,
        )
    }

    companion object {
        fun fromDto(dto: Track) =
            TrackEntity(
                dto.id,
                dto.file,
                dto.isPlayed,
            )
    }
}

fun List<TrackEntity>.toDto(): List<Track> = map(TrackEntity::toDto)
fun List<Track>.toEntity(): List<TrackEntity> = map(TrackEntity::fromDto)