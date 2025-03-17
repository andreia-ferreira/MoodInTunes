package net.penguin.data

import net.penguin.data.local.database.MoodInTunesDatabase
import net.penguin.data.mapper.PlaylistDetailMapper
import net.penguin.data.mapper.SongMapper
import net.penguin.domain.entity.Playlist
import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.repository.CollectionRepository
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val database: MoodInTunesDatabase,
): CollectionRepository {
    override suspend fun savePlaylist(playlistDetail: PlaylistDetail) {
        val playlistData = PlaylistDetailMapper.map(playlistDetail)
        val songData = playlistDetail.songList.map { SongMapper.map(playlistDetail.id, it) }
        database.playlistDao().insertPlaylistWithSongs(playlistData, songData)
    }

    override suspend fun getSavedPlaylists(): List<Playlist> {
        val data = database.playlistDao().getAllPlaylistsWithSongs()
        return data.map {
            Playlist(
                id = it.playlist.id,
                name = it.playlist.title,
                trackNumber = it.playlist.trackNumber,
                thumbnail = it.playlist.pictureUrl,
                isSaved = true
            )
        }
    }

    override suspend fun getPlaylistDetails(id: Long): PlaylistDetail {
        val data = database.playlistDao().getPlaylistWithSongs(id)
        return PlaylistDetailMapper.map(data)
    }

    override suspend fun removePlaylist(id: Long) {
        database.playlistDao().deleteSongsByPlaylist(id)
    }
}