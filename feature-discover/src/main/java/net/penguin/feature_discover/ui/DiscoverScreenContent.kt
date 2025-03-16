@file:OptIn(ExperimentalMaterial3Api::class)

package net.penguin.feature_discover.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import net.penguin.common_design.DevicePreviews
import net.penguin.common_design.theme.ContentMaxWidth
import net.penguin.common_design.theme.MoodInTunesTheme
import net.penguin.common_design.theme.PaddingBig
import net.penguin.common_design.theme.PaddingDefault
import net.penguin.common_design.theme.PaddingSmall
import net.penguin.component_playlist.ui.PlaylistCard
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
                currentQuery = screenState.selectedMood?.let { stringResource(it.titleRes) },
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
    if (screenState.moodList.size == 1) {
        BackHandler {
            onAction(DiscoverScreenAction.OnMoodUnselected)
        }
    }

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
                    .animateItem(
                        fadeInSpec = tween(durationMillis = 250),
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
    currentQuery: String? = null,
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
                val listState = rememberLazyListState()
                val shouldLoadMore = remember {
                    derivedStateOf {
                        val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                        val totalItemsCount = listState.layoutInfo.totalItemsCount

                        lastVisibleItemIndex != null && lastVisibleItemIndex == totalItemsCount - 1
                    }
                }

                LaunchedEffect(shouldLoadMore.value) {
                    if (shouldLoadMore.value) {
                        currentQuery?.let {
                            onAction(DiscoverScreenAction.OnEndOfListReached(it, listState.layoutInfo.totalItemsCount))
                        }
                    }
                }

                LazyColumn(state = listState) {
                    items(
                        items = searchState.searchResults,
                        key = { it.id }
                    ) { playlist ->
                        PlaylistCard(
                            modifier = Modifier.padding(vertical = PaddingSmall),
                            playlist = playlist,
                            onClick = { onAction(DiscoverScreenAction.OnSearchResultClicked(playlist))}
                        )
                    }
                }
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