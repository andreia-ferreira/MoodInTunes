package net.penguin.feature_collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import net.penguin.domain.entity.Playlist
import net.penguin.domain.usecase.GetAllCollectionUseCase
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    getAllCollectionUseCase: GetAllCollectionUseCase
): ViewModel() {
    val collectionList: StateFlow<List<Playlist>> = getAllCollectionUseCase.execute()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}