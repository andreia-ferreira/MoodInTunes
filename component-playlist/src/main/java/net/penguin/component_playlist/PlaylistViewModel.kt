package net.penguin.component_playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.penguin.common_design.navigation.PlaylistDetailNavigation
import net.penguin.common_ui.R
import net.penguin.component_playlist.model.PlaylistScreenState
import net.penguin.domain.usecase.GetPlaylistDetailUseCase
import net.penguin.domain.usecase.RemoveFromCollectionUseCase
import net.penguin.domain.usecase.SaveToCollectionUseCase
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPlaylistDetailUseCase: GetPlaylistDetailUseCase,
    private val saveToCollectionUseCase: SaveToCollectionUseCase,
    private val removeFromCollectionUseCase: RemoveFromCollectionUseCase
) : ViewModel() {
    private val _playlistScreenState: MutableStateFlow<PlaylistScreenState> = MutableStateFlow(PlaylistScreenState.Loading)
    val playlistScreenState: StateFlow<PlaylistScreenState> = _playlistScreenState

    init {
        viewModelScope.launch {
            val playlistDetail = savedStateHandle.toRoute<PlaylistDetailNavigation>()
            getPlaylistDetailUseCase.execute(GetPlaylistDetailUseCase.RequestParams(playlistDetail.playlistId))
                .onSuccess {
                    _playlistScreenState.value = PlaylistScreenState.Content(it)
                }
                .onFailure {
                    _playlistScreenState.value = PlaylistScreenState.Error(R.string.generic_error)
                }
        }
    }

    fun saveToCollection() {
        viewModelScope.launch {
            (_playlistScreenState.value as? PlaylistScreenState.Content)?.let {
                saveToCollectionUseCase.execute(SaveToCollectionUseCase.RequestParams(it.playlist))
                _playlistScreenState.value = it.copy(playlist = it.playlist.copy(isSaved = true))
            }
        }
    }

    fun removeFromCollection() {
        viewModelScope.launch {
            (_playlistScreenState.value as? PlaylistScreenState.Content)?.let {
                removeFromCollectionUseCase.execute(RemoveFromCollectionUseCase.RequestParams(it.playlist.id))
                _playlistScreenState.value = it.copy(playlist = it.playlist.copy(isSaved = false))
            }
        }
    }
}