package net.penguin.domain.usecase

import net.penguin.domain.entity.Playlist
import net.penguin.domain.repository.CollectionRepository
import javax.inject.Inject

class GetAllCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
): UseCase.NoParamsUseCase<List<Playlist>> {
    override suspend fun execute(): List<Playlist> {
        return collectionRepository.getSavedPlaylists()
    }
}