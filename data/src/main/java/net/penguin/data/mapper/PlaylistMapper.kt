package net.penguin.data.mapper

import net.penguin.data.model.PlaylistSearchResult
import net.penguin.domain.entity.Playlist

object PlaylistMapper {
    fun map(data: PlaylistSearchResult.Playlist, isSaved: Boolean): Playlist {
        return Playlist(
            id = data.id,
            name = data.title,
            trackNumber = data.nbTracks,
            thumbnail = data.pictureMedium,
            isSaved = isSaved
        )
    }
}