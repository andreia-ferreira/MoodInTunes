package net.penguin.feature_discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.penguin.common_ui.R
import net.penguin.domain.entity.Mood
import net.penguin.domain.usecase.SearchByMoodUseCase
import net.penguin.feature_discover.mapper.MoodMapper
import net.penguin.feature_discover.model.DiscoverScreenState
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val searchByMoodUseCase: SearchByMoodUseCase,
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

    fun onEndOfListReached(currentQuery: String, currentIndex: Int) {
        searchByMood(currentQuery, currentIndex)
    }

    private fun searchByMood(query: String, currentIndex: Int = 0) {
        _discoverScreenState.value = _discoverScreenState.value.copy(isLoading = true)
        viewModelScope.launch {
            searchByMoodUseCase.execute(SearchByMoodUseCase.RequestParams(query, currentIndex))
                .collect { result ->
                    _discoverScreenState.value = _discoverScreenState.value.copy(
                        isLoading = false,
                        searchState = if (result.isSuccess) {
                            DiscoverScreenState.SearchState.Success(result.getOrDefault(emptyList()))
                        } else {
                            DiscoverScreenState.SearchState.Error(R.string.generic_error)
                        }
                    )
                }
        }
    }
}