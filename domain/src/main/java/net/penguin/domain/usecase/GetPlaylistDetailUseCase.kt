package net.penguin.domain.usecase

import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.repository.SearchRepository

class GetPlaylistDetailUseCase(
    private val searchRepository: SearchRepository
): UseCase.ParamsUseCase<GetPlaylistDetailUseCase.RequestParams, Result<PlaylistDetail>> {
    override suspend fun execute(requestParams: RequestParams): Result<PlaylistDetail> {
        return searchRepository.getPlaylistDetails(requestParams.playlistId)
    }

    class RequestParams(val playlistId: Long)
}