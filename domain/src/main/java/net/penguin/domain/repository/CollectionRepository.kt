package net.penguin.domain.repository

import kotlinx.coroutines.flow.Flow
import net.penguin.domain.entity.Playlist
import net.penguin.domain.entity.PlaylistDetail

interface CollectionRepository {
    suspend fun savePlaylist(playlistDetail: PlaylistDetail)
    fun getSavedPlaylists(): Flow<List<Playlist>>
    suspend fun getPlaylistDetails(id: Long): Result<PlaylistDetail>
    suspend fun removePlaylist(id: Long)
}