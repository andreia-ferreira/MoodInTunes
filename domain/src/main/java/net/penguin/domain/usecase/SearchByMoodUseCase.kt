package net.penguin.domain.usecase

import kotlinx.coroutines.flow.Flow
import net.penguin.domain.entity.Playlist
import net.penguin.domain.repository.SearchRepository
import net.penguin.domain.usecase.UseCase.ParamsUseCase
import javax.inject.Inject

class SearchByMoodUseCase @Inject constructor(
    private val searchRepository: SearchRepository
): ParamsUseCase<SearchByMoodUseCase.RequestParams, Flow<Result<List<Playlist>>>> {
    override suspend fun execute(requestParams: RequestParams): Flow<Result<List<Playlist>>> {
        return searchRepository.searchPlaylists(query = requestParams.query, currentIndex = requestParams.currentIndex)
    }

    class RequestParams(val query: String, val currentIndex: Int)
}