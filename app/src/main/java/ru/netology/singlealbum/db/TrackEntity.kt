package ru.netology.singlealbum.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.singlealbum.dto.Track

@Entity
data class TrackEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val file: String,
) {
    fun toDto(): Track {
        return Track(
            id,
            file,
        )
    }

    companion object {
        fun fromDto(dto: Track) =
            TrackEntity(
                dto.id,
                dto.file,
            )
    }
}

fun List<TrackEntity>.toDto(): List<Track> = map(TrackEntity::toDto)
fun List<Track>.toEntity(): List<TrackEntity> = map(TrackEntity::fromDto)