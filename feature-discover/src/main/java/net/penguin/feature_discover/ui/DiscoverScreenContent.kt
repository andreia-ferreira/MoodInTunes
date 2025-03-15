@file:OptIn(ExperimentalMaterial3Api::class)

package net.penguin.feature_discover.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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

        Box {
            SearchContent(
                modifier = Modifier.padding(PaddingDefault),
                searchState = screenState.searchState,
                onAction = onAction
            )
            if (screenState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = PaddingBig)
                        .align(Alignment.Center)
                )
            }
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
        columns = if (screenState.moodList.size == 1) {
            GridCells.Fixed(1)
        } else {
            GridCells.Adaptive(minSize = dimensionResource(R.dimen.mood_item_min_width))
        },
        contentPadding = PaddingValues(horizontal = PaddingDefault)
    ) {
        items(items = screenState.moodList, key = { it.mood}) { state ->
            val label = stringResource(state.titleRes)
            val isSelected = screenState.selectedMood == state
            MoodItem(
                modifier = Modifier
                    .padding(PaddingSmall)
                    .animateItem(fadeInSpec = tween(durationMillis = 250),
                        fadeOutSpec = tween(durationMillis = 100),
                        placementSpec = spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = Spring.DampingRatioLowBouncy)
                    ),
                moodItem = state,
                isSelected = isSelected,
                onClick = {
                    if (isSelected) {
                        onAction(DiscoverScreenAction.OnMoodUnselected)
                    } else {
                        onAction(DiscoverScreenAction.OnMoodSelected(state, label))
                    }
                }
            )
        }
    }
}

@Composable
private fun MoodItem(
    modifier: Modifier = Modifier,
    moodItem: DiscoverScreenState.MoodItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val label = stringResource(moodItem.titleRes)
    FilterChip(
        modifier = modifier,
        selected = isSelected,
        leadingIcon = {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Unselect"
                )
            }
        },
        label = {
            Text(
                modifier = Modifier
                    .padding(vertical = PaddingDefault)
                    .fillMaxWidth(),
                text = label,
                textAlign = TextAlign.Center
            )
        },
        onClick = onClick
    )
}

@Composable
private fun SearchContent(
    modifier: Modifier = Modifier,
    searchState: DiscoverScreenState.SearchState,
    onAction: (DiscoverScreenAction) -> Unit
) {
    Column(modifier) {
        Crossfade(searchState) {
            when (it) {
                is DiscoverScreenState.SearchState.Error -> Text(
                    text = stringResource(it.messageRes)
                )
                DiscoverScreenState.SearchState.Idle -> {}
                is DiscoverScreenState.SearchState.Success -> {
                    LazyColumn {
                        items(it.searchResults) { playlist ->
                            PlaylistCard(
                                modifier = Modifier.padding(vertical = PaddingSmall),
                                playlist = playlist,
                                onAction = onAction
                            )
                        }
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
        val moodList = Mood.entries.map { MoodMapper.map(it) }
        DiscoverScreenContent(
            screenState = DiscoverScreenState(
                isLoading = false,
                moodList = moodList,
                selectedMood = moodList.first(),
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