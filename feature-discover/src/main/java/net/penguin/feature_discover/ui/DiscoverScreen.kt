package net.penguin.feature_discover.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.penguin.domain.entity.Mood
import net.penguin.domain.entity.Playlist
import net.penguin.feature_discover.DiscoverViewModel

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val screenState by viewModel.discoverScreenState.collectAsStateWithLifecycle()

    DiscoverScreenContent(
        modifier = modifier,
        screenState = screenState,
        onAction = {
            when (it) {
                is DiscoverScreenAction.OnMoodSelected -> viewModel.onMoodSelected(it.mood, it.name)
                is DiscoverScreenAction.OnSearchResultClicked -> TODO()
            }
        }
    )
}

sealed interface DiscoverScreenAction {
    data class OnMoodSelected(val mood: Mood, val name: String): DiscoverScreenAction
    data class OnSearchResultClicked(val playlist: Playlist): DiscoverScreenAction
}
