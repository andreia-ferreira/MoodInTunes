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
    private val fullMoodList = Mood.entries.map { MoodMapper.map(it) }
    private val _discoverScreenState: MutableStateFlow<DiscoverScreenState> = MutableStateFlow(
        DiscoverScreenState(
            isLoading = false,
            moodList = fullMoodList,
            selectedMood = null,
            searchState = DiscoverScreenState.SearchState.Idle
        )
    )
    val discoverScreenState: StateFlow<DiscoverScreenState> = _discoverScreenState

    fun onMoodSelected(mood: DiscoverScreenState.MoodItem, query: String) {
        _discoverScreenState.value = _discoverScreenState.value.copy(
            selectedMood = mood,
            moodList = listOf(mood)
        )
        searchByMood(query)
    }

    fun onMoodUnselected() {
        _discoverScreenState.value = _discoverScreenState.value.copy(
            selectedMood = null,
            moodList = fullMoodList,
            searchState = DiscoverScreenState.SearchState.Idle
        )
    }

    private fun searchByMood(query: String) {
        _discoverScreenState.value = _discoverScreenState.value.copy(isLoading = true)
        viewModelScope.launch {
            searchByMoodUseCase.execute(SearchByMoodUseCase.RequestParams(query))
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