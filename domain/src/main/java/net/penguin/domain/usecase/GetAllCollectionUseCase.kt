package net.penguin.domain.usecase

import kotlinx.coroutines.flow.Flow
import net.penguin.domain.entity.Playlist
import net.penguin.domain.repository.CollectionRepository
import javax.inject.Inject

class GetAllCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
): UseCase.NoParamsFlowUseCase<Flow<List<Playlist>>> {
    override fun execute(): Flow<List<Playlist>> {
        return collectionRepository.getSavedPlaylists()
    }
}