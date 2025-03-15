package net.penguin.data.mapper

import net.penguin.data.model.PlaylistDetailResult
import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.entity.Song

object PlaylistDetailMapper {
    fun map(source: PlaylistDetailResult): PlaylistDetail {
        return PlaylistDetail(
            id = source.id,
            name = source.title,
            description = source.description,
            trackNumber = source.nbTracks,
            duration = source.duration,
            picture = source.pictureBig,
            creator = source.creator.name,
            songList = source.tracks.data.map {
                Song(
                    id = it.id,
                    title = it.title,
                    artist = it.artist.name,
                    previewUrl = it.preview
                )
            }
        )

    }
}