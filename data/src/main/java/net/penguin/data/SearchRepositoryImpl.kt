package net.penguin.data

import net.penguin.data.mapper.PlaylistMapper
import net.penguin.data.remote.DeezerApiService
import net.penguin.domain.entity.Playlist
import net.penguin.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val deezerApiService: DeezerApiService
): SearchRepository {
    override suspend fun searchPlaylists(query: String): Result<List<Playlist>> {
        return try {
            val result = deezerApiService.searchPlaylist(query)
            Result.success(PlaylistMapper.map(result.body()!!))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}