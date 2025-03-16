package net.penguin.data.mapper

import net.penguin.data.model.PlaylistJson
import net.penguin.domain.entity.Playlist

object PlaylistMapper {
    fun map(data: PlaylistJson): Playlist {
        return Playlist(
            id = data.id,
            name = data.title,
            trackNumber = data.nbTracks,
            thumbnail = data.pictureMedium
        )
    }
}