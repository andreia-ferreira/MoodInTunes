package net.penguin.data.mapper

import net.penguin.data.local.database.entity.PlaylistLocalData
import net.penguin.data.local.database.entity.PlaylistWithSongs
import net.penguin.data.model.PlaylistDetailResult
import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.entity.Song

object PlaylistDetailMapper {
    fun map(source: PlaylistDetailResult, isSaved: Boolean): PlaylistDetail {
        return PlaylistDetail(
            id = source.id,
            isSaved = isSaved,
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

    fun map(data: PlaylistDetail): PlaylistLocalData {
        return PlaylistLocalData(
            id = data.id,
            title = data.name,
            creator = data.creator,
            description = data.description,
            duration = data.duration,
            trackNumber = data.trackNumber,
            pictureUrl = data.picture,
        )
    }

    fun map(data: PlaylistWithSongs): PlaylistDetail {
        return PlaylistDetail(
            id = data.playlist.id,
            isSaved = true,
            name = data.playlist.title,
            description = data.playlist.description,
            trackNumber = data.playlist.trackNumber,
            duration = data.playlist.duration,
            picture = data.playlist.pictureUrl,
            creator = data.playlist.creator,
            songList = data.tracks.map {
                SongMapper.map(it)
            }
        )
    }
}