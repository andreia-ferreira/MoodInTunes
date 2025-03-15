package net.penguin.feature_discover.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import net.penguin.common_design.DevicePreviews
import net.penguin.common_design.theme.ContentMaxWidth
import net.penguin.common_design.theme.CornerRadiusDefault
import net.penguin.common_design.theme.MoodInTunesTheme
import net.penguin.common_design.theme.PaddingBig
import net.penguin.common_design.theme.PaddingDefault
import net.penguin.common_design.theme.PaddingSmall
import net.penguin.domain.entity.Mood
import net.penguin.domain.entity.Playlist
import net.penguin.feature_discover.R
import net.penguin.feature_discover.mapper.MoodMapper
import net.penguin.feature_discover.model.DiscoverScreenState

@Composable
fun DiscoverScreenContent(
    modifier: Modifier = Modifier,
    screenState: DiscoverScreenState,
    onAction: (DiscoverScreenAction) -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .widthIn(max = ContentMaxWidth),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(horizontal = PaddingDefault, vertical = PaddingBig),
                text = stringResource(R.string.discover_header)
            )

            MoodSelection(
                modifier = Modifier.padding(PaddingDefault),
                screenState = screenState,
                onAction = onAction
            )

            SearchContent(
                modifier = Modifier.padding(PaddingDefault),
                searchState = screenState.searchState,
                onAction = onAction
            )
        }

        if (screenState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun MoodSelection(
    modifier: Modifier = Modifier,
    screenState: DiscoverScreenState,
    onAction: (DiscoverScreenAction) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(horizontal = PaddingDefault)
    ) {
        items(screenState.moodList) {
            val label = stringResource(it.titleRes)
            FilterChip(
                modifier = Modifier.padding(PaddingSmall),
                selected = screenState.selectedMood == it.mood,
                label = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = PaddingDefault)
                            .fillMaxWidth(),
                        text = label,
                        textAlign = TextAlign.Center
                    )
                },
                onClick = {
                    onAction(DiscoverScreenAction.OnMoodSelected(it.mood, label))
                }
            )
        }
    }
}

@Composable
private fun SearchContent(
    modifier: Modifier = Modifier,
    searchState: DiscoverScreenState.SearchState,
    onAction: (DiscoverScreenAction) -> Unit
) {
    Column(modifier) {
        when (searchState) {
            is DiscoverScreenState.SearchState.Error -> Text(
                text = stringResource(searchState.messageRes)
            )
            DiscoverScreenState.SearchState.Idle -> {}
            is DiscoverScreenState.SearchState.Success -> {
                LazyColumn {
                    items(searchState.searchResults) {
                        PlaylistCard(
                            modifier = Modifier.padding(vertical = PaddingSmall),
                            playlist = it,
                            onAction = onAction
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaylistCard(
    modifier: Modifier = Modifier,
    playlist: Playlist,
    onAction: (DiscoverScreenAction) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onAction(DiscoverScreenAction.OnSearchResultClicked(playlist))
            }
    ) {
        Row(Modifier) {
            AsyncImage(
                modifier = Modifier
                    .padding(PaddingDefault)
                    .size(dimensionResource(R.dimen.playlist_image_size))
                    .clip(RoundedCornerShape(CornerSize(CornerRadiusDefault))),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(playlist.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = playlist.name,
                contentScale = ContentScale.Crop,
            )

            Column(modifier = Modifier.padding(PaddingDefault)) {
                Text(text = playlist.name)
                Text(
                    text = stringResource(
                        R.string.playlist_number_of_tracks,
                        playlist.trackNumber
                    )
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun DiscoverScreenContentPreview() {
    MoodInTunesTheme {
        DiscoverScreenContent(
            screenState = DiscoverScreenState(
                isLoading = false,
                moodList = Mood.entries.map { MoodMapper.map(it) },
                selectedMood = Mood.FUN,
                searchState = DiscoverScreenState.SearchState.Success(
                    listOf(
                        Playlist(
                            id = 1L,
                            name = "Mood playlist",
                            trackNumber = 666,
                            thumbnail = ""
                        )
                    )
                )
            ),
            onAction = {}
        )
    }
}