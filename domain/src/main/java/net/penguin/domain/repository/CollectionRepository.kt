package net.penguin.domain.repository

import net.penguin.domain.entity.Playlist
import net.penguin.domain.entity.PlaylistDetail

interface CollectionRepository {
    suspend fun savePlaylist(playlistDetail: PlaylistDetail)
    suspend fun getSavedPlaylists(): List<Playlist>
    suspend fun getPlaylistDetails(id: Long): PlaylistDetail
    suspend fun removePlaylist(id: Long)
}