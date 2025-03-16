package net.penguin.domain.usecase

import net.penguin.domain.repository.CollectionRepository
import javax.inject.Inject

class RemoveFromCollectionUseCase@Inject constructor(
    private val collectionRepository: CollectionRepository
) : UseCase.ParamsUseCase<RemoveFromCollectionUseCase.RequestParams, Unit> {
    override suspend fun execute(requestParams: RequestParams) {
        collectionRepository.removePlaylist(requestParams.playlistId)
    }

    class RequestParams(val playlistId: Long)
}