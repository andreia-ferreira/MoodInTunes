package net.penguin.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.penguin.data.local.CachedSearchData
import net.penguin.data.local.SearchMemoryDataSource
import net.penguin.data.mapper.PlaylistDetailMapper
import net.penguin.data.mapper.PlaylistMapper
import net.penguin.data.remote.DeezerApiService
import net.penguin.domain.entity.Playlist
import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.repository.SearchRepository
import timber.log.Timber
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: DeezerApiService,
    private val localDataSource: SearchMemoryDataSource
): SearchRepository {

    override suspend fun searchPlaylists(query: String, currentIndex: Int): Flow<Result<List<Playlist>>> {
        return flow {
            try {
                val cachedData = localDataSource.get(query)
                if (cachedData == null || cachedData.results.isEmpty() || cachedData.currentIndex < currentIndex) {
                    val remoteData = apiService.searchPlaylist(
                        query = query,
                        limit = PAGE_SIZE,
                        index = currentIndex
                    )
                    val newCachedList = cachedData?.results.orEmpty().toMutableList()
                    newCachedList.addAll(remoteData.body()?.data.orEmpty())
                    localDataSource.set(query, CachedSearchData(currentIndex, newCachedList))
                }
                emit(Result.success(PlaylistMapper.map(localDataSource.get(query)?.results.orEmpty())))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Result.failure(e))
            }
        }
    }

    override suspend fun getPlaylistDetails(playlistId: Long): Result<PlaylistDetail> {
        return try {
            val result = apiService.getPlaylistDetails(playlistId)
            Result.success(PlaylistDetailMapper.map(result.body()!!))
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure(e)
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}