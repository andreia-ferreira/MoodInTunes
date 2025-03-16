package net.penguin.feature_collection

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.penguin.common_design.theme.PaddingDefault
import net.penguin.common_design.theme.PaddingSmall
import net.penguin.component_playlist.ui.PlaylistCard

@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    viewModel: CollectionViewModel = hiltViewModel(),
    goToPlaylistDetails: (Long) -> Unit
) {
    val collection by viewModel.collectionList.collectAsStateWithLifecycle()

    LazyColumn(modifier.padding(PaddingDefault)) {
        items(collection) { playlist ->
            PlaylistCard(
                modifier = Modifier.padding(vertical = PaddingSmall),
                playlist = playlist,
                onClick = { goToPlaylistDetails(playlist.id) }
            )
        }
    }
}