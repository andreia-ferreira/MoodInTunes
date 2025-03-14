package net.penguin.domain.usecase

import net.penguin.domain.entity.Playlist
import net.penguin.domain.repository.SearchRepository
import net.penguin.domain.usecase.UseCase.ParamsUseCase
import javax.inject.Inject

class SearchByMoodUseCase @Inject constructor(
    private val searchRepository: SearchRepository
): ParamsUseCase<SearchByMoodUseCase.RequestParameters, Result<List<Playlist>>> {
    override suspend fun execute(requestParams: RequestParameters): Result<List<Playlist>> {
        return searchRepository.searchPlaylists(query = requestParams.query)
    }

    class RequestParameters(val query: String)
}