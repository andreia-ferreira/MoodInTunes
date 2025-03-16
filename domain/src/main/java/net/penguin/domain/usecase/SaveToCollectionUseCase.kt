package net.penguin.domain.usecase

import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.repository.CollectionRepository
import javax.inject.Inject

class SaveToCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) : UseCase.ParamsUseCase<SaveToCollectionUseCase.RequestParams, Unit> {
    override suspend fun execute(requestParams: RequestParams) {
        collectionRepository.savePlaylist(requestParams.playlist)
    }

    class RequestParams(val playlist: PlaylistDetail)
}