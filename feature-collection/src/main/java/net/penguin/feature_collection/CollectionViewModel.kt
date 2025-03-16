package net.penguin.feature_collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.penguin.domain.entity.Playlist
import net.penguin.domain.usecase.GetAllCollectionUseCase
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getAllCollectionUseCase: GetAllCollectionUseCase
): ViewModel() {
    private val _collectionList = MutableStateFlow<List<Playlist>>(emptyList())
    val collectionList: StateFlow<List<Playlist>> = _collectionList

    init {
        viewModelScope.launch {
            _collectionList.value = getAllCollectionUseCase.execute()
        }
    }
}