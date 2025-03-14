package net.penguin.feature_discover

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.penguin.domain.entity.Playlist
import net.penguin.domain.usecase.SearchByMoodUseCase
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val searchByMoodUseCase: SearchByMoodUseCase
): ViewModel() {
    private val _searchByMoodResult = MutableStateFlow<List<Playlist>>(emptyList())
    val searchByMoodResult: StateFlow<List<Playlist>> = _searchByMoodResult

    fun searchByMood(query: String) {
        viewModelScope.launch {
            searchByMoodUseCase.execute(SearchByMoodUseCase.RequestParameters(query))
                .onSuccess {
                    _searchByMoodResult.value = it
                }
                .onFailure {
                    Log.d("xpto", "$it")
                }
        }
    }
}