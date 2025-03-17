package net.penguin.feature_discover.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.penguin.domain.entity.Playlist
import net.penguin.feature_discover.DiscoverViewModel
import net.penguin.feature_discover.model.DiscoverScreenState

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = hiltViewModel(),
    goToPlaylistDetails: (Long, Boolean) -> Unit
) {
    val screenState by viewModel.discoverScreenState.collectAsStateWithLifecycle()

    DiscoverScreenContent(
        modifier = modifier,
        screenState = screenState,
        onAction = {
            when (it) {
                is DiscoverScreenAction.OnMoodSelected -> viewModel.onMoodSelected(it.mood, it.name)
                is DiscoverScreenAction.OnMoodUnselected -> viewModel.onMoodUnselected()
                is DiscoverScreenAction.OnSearchResultClicked -> goToPlaylistDetails(it.playlist.id, it.playlist.isSaved)
                is DiscoverScreenAction.OnEndOfListReached -> viewModel.onEndOfListReached(it.currentQuery, it.currentIndex)
            }
        }
    )
}

sealed interface DiscoverScreenAction {
    data class OnMoodSelected(val mood: DiscoverScreenState.MoodItem, val name: String): DiscoverScreenAction
    data object OnMoodUnselected: DiscoverScreenAction
    data class OnSearchResultClicked(val playlist: Playlist): DiscoverScreenAction
    data class OnEndOfListReached(val currentQuery: String, val currentIndex: Int): DiscoverScreenAction
}
