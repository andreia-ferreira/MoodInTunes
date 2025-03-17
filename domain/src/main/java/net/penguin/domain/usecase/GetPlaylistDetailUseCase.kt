package net.penguin.domain.usecase

import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.repository.CollectionRepository
import net.penguin.domain.repository.SearchRepository

class GetPlaylistDetailUseCase(
    private val searchRepository: SearchRepository,
    private val collectionRepository: CollectionRepository
): UseCase.ParamsUseCase<GetPlaylistDetailUseCase.RequestParams, Result<PlaylistDetail>> {
    override suspend fun execute(requestParams: RequestParams): Result<PlaylistDetail> {
        return if (requestParams.isSaved) {
            collectionRepository.getPlaylistDetails(requestParams.playlistId)
        } else {
            searchRepository.getPlaylistDetails(requestParams.playlistId)
        }
    }

    class RequestParams(val playlistId: Long, val isSaved: Boolean)
}