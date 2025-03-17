package net.penguin.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import net.penguin.data.local.cache.SearchMemoryDataSource
import net.penguin.data.local.database.MoodInTunesDatabase
import net.penguin.data.mapper.PlaylistDetailMapper
import net.penguin.data.mapper.PlaylistMapper
import net.penguin.data.model.CachedSearchData
import net.penguin.data.model.PlaylistSearchResult
import net.penguin.data.remote.DeezerApiService
import net.penguin.domain.entity.Playlist
import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.repository.SearchRepository
import timber.log.Timber
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: DeezerApiService,
    private val database: MoodInTunesDatabase,
    private val searchCache: SearchMemoryDataSource
): SearchRepository {

    override suspend fun searchPlaylists(query: String, currentIndex: Int): Flow<Result<List<Playlist>>> {
        return flow {
            val cachedData = searchCache.get(query)
            if (cachedData == null || cachedData.results.isEmpty() || cachedData.currentIndex < currentIndex) {
                refreshData(query, currentIndex, cachedData)
            }

            try {
                val mappedData = searchCache.get(query)?.results!!.map { PlaylistMapper.map(it.data, it.isSaved) }
                emit(Result.success(mappedData))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Result.failure(e))
            }
        }
    }

    override suspend fun getPlaylistDetails(playlistId: Long): Result<PlaylistDetail> {
        return try {
            val result = apiService.getPlaylistDetails(playlistId)
            val isSaved = isPlaylistInCollection(playlistId)
            Result.success(PlaylistDetailMapper.map(result.body()!!, isSaved))
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure(e)
        }
    }

    private suspend fun refreshData(query: String, currentIndex: Int, currentCache: CachedSearchData?) {
        try {
            val newCachedList = currentCache?.results.orEmpty().toMutableList()
            val remoteData = apiService.searchPlaylist(
                query = query,
                limit = PAGE_SIZE,
                index = currentIndex
            )

            if (!remoteData.body()?.data.isNullOrEmpty()) {
                remoteData.body()?.data?.forEach { playlist ->
                    val isSaved = isPlaylistInCollection(playlist.id)
                    if (isSaved) {
                        updateDatabase(playlist)
                    }
                    newCachedList.add(CachedSearchData.Playlist(isSaved = isSaved, data = playlist))
                }
                searchCache.set(query, CachedSearchData(currentIndex, newCachedList))
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private suspend fun updateDatabase(remoteData: PlaylistSearchResult.Playlist) {
        val updatedPlaylistData = database.playlistDao().getPlaylistWithSongs(remoteData.id).playlist.copy(
            title = remoteData.title,
            pictureUrl = remoteData.pictureMedium,
            trackNumber = remoteData.nbTracks
        )
        database.playlistDao().updatePlaylist(updatedPlaylistData)
    }

    private suspend fun isPlaylistInCollection(id: Long): Boolean {
        val savedPlaylistIds = database.playlistDao().getAllPlaylistsWithSongs().first().map { it.playlist.id }
        return id in savedPlaylistIds
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}