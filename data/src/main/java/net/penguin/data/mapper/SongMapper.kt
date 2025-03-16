package net.penguin.data.mapper

import net.penguin.data.local.database.entity.SongLocalData
import net.penguin.domain.entity.Song

object SongMapper {
    fun map(playlistId: Long, data: Song): SongLocalData {
        return SongLocalData(
            id = data.id,
            title = data.title,
            playlistId = playlistId,
            artist = data.artist,
            previewUrl = data.previewUrl
        )
    }

    fun map(data: SongLocalData): Song {
        return Song(
            id = data.id,
            title = data.title,
            artist = data.artist,
            previewUrl = data.previewUrl
        )
    }
}