package net.penguin.component_playlist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.penguin.component_playlist.PlaylistViewModel

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val state by viewModel.playlistScreenState.collectAsStateWithLifecycle()

    PlaylistScreenContent(
        modifier = modifier,
        state = state,
        onAction = {
            when (it) {
                PlaylistScreenAction.OnBackClicked -> onBackClicked()
            }
        }
    )
}

sealed interface PlaylistScreenAction {
    data object OnBackClicked: PlaylistScreenAction
}