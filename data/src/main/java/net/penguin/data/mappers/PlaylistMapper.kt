package net.penguin.data.mappers

import net.penguin.data.model.PlaylistSearchResult
import net.penguin.domain.entity.Playlist

object PlaylistMapper {
    fun fromData(data: PlaylistSearchResult): List<Playlist> {
        return data.data.map {
            Playlist(
                id = it.id,
                name = it.title,
                trackNumber = it.nbTracks,
                thumbnail = it.pictureSmall
            )
        }
    }
}