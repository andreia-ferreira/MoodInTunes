package net.penguin.domain.repository

import kotlinx.coroutines.flow.Flow
import net.penguin.domain.entity.Playlist
import net.penguin.domain.entity.PlaylistDetail

interface SearchRepository {
    suspend fun searchPlaylists(query: String, currentIndex: Int): Flow<Result<List<Playlist>>>
    suspend fun getPlaylistDetails(playlistId: Long): Result<PlaylistDetail>
}