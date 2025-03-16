package net.penguin.data.mapper

import net.penguin.data.model.PlaylistJson
import net.penguin.domain.entity.Playlist

object PlaylistMapper {
    fun map(data: List<PlaylistJson>): List<Playlist> {
        return data.map {
            Playlist(
                id = it.id,
                name = it.title,
                trackNumber = it.nbTracks,
                thumbnail = it.pictureMedium
            )
        }
    }
}