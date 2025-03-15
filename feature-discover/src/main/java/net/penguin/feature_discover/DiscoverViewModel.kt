package net.penguin.feature_discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.penguin.domain.entity.Mood
import net.penguin.domain.usecase.SearchByMoodUseCase
import net.penguin.feature_discover.mapper.MoodMapper
import net.penguin.feature_discover.model.DiscoverScreenState
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val searchByMoodUseCase: SearchByMoodUseCase
): ViewModel() {
    private val _discoverScreenState: MutableStateFlow<DiscoverScreenState> = MutableStateFlow(
        DiscoverScreenState(
            isLoading = false,
            moodList = Mood.entries.map { MoodMapper.map(it) },
            selectedMood = null,
            searchState = DiscoverScreenState.SearchState.Idle
        )
    )
    val discoverScreenState: StateFlow<DiscoverScreenState> = _discoverScreenState

    fun onMoodSelected(mood: Mood, query: String) {
        _discoverScreenState.value = _discoverScreenState.value.copy(selectedMood = mood)
        searchByMood(query)
    }

    private fun searchByMood(query: String) {
        _discoverScreenState.value = _discoverScreenState.value.copy(isLoading = true)
        viewModelScope.launch {
            searchByMoodUseCase.execute(SearchByMoodUseCase.RequestParameters(query))
                .onSuccess {
                    _discoverScreenState.value = _discoverScreenState.value.copy(
                        isLoading = false,
                        searchState = DiscoverScreenState.SearchState.Success(it)
                    )
                }
                .onFailure {
                    _discoverScreenState.value = _discoverScreenState.value.copy(
                        isLoading = false,
                        searchState = DiscoverScreenState.SearchState.Error(R.string.discover_generic_error)
                    )
                }
        }
    }
}