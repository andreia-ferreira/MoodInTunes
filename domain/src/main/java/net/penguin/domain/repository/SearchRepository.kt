package net.penguin.domain.repository

import net.penguin.domain.entity.Playlist
import net.penguin.domain.entity.PlaylistDetail

interface SearchRepository {
    suspend fun searchPlaylists(query: String): Result<List<Playlist>>
    suspend fun getPlaylistDetails(playlistId: Long): Result<PlaylistDetail>
}