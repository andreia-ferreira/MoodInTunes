package net.penguin.feature_collection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.penguin.common_design.theme.PaddingDefault
import net.penguin.common_design.theme.PaddingSmall
import net.penguin.component_playlist.ui.PlaylistCard

@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    viewModel: CollectionViewModel = hiltViewModel(),
    goToPlaylistDetails: (Long, Boolean) -> Unit
) {
    val collection by viewModel.collectionList.collectAsStateWithLifecycle()

    if (collection.isEmpty()) {
        Box(modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.empty_collection),
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.padding(PaddingDefault),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(collection) { playlist ->
                PlaylistCard(
                    modifier = Modifier.padding(vertical = PaddingSmall),
                    playlist = playlist,
                    onClick = { goToPlaylistDetails(playlist.id, true) }
                )
            }
        }
    }
}