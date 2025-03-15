package net.penguin.feature_discover.model

import androidx.annotation.StringRes
import net.penguin.domain.entity.Mood
import net.penguin.domain.entity.Playlist

data class DiscoverScreenState(
    val isLoading: Boolean,
    val moodList: List<MoodItem>,
    val selectedMood: MoodItem?,
    val searchState: SearchState
) {
    data class MoodItem(
        @StringRes val titleRes: Int,
        val mood: Mood
    )
    sealed class SearchState {
        data class Success(val searchResults: List<Playlist>): SearchState()
        data class Error(@StringRes val messageRes: Int): SearchState()
        data object Idle: SearchState()
    }
}