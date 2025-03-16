@file:OptIn(ExperimentalMaterial3Api::class)

package net.penguin.component_playlist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import net.penguin.common_design.DevicePreviews
import net.penguin.common_design.theme.ContentMaxWidth
import net.penguin.common_design.theme.CornerRadiusDefault
import net.penguin.common_design.theme.MoodInTunesTheme
import net.penguin.common_design.theme.PaddingDefault
import net.penguin.common_design.theme.PaddingSmall
import net.penguin.component_playlist.R
import net.penguin.component_playlist.model.PlaylistScreenState
import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.entity.Song

@Composable
fun PlaylistScreenContent(
    modifier: Modifier = Modifier,
    state: PlaylistScreenState,
    onAction: (PlaylistScreenAction) -> Unit
) {
    Column(modifier) {
        PageHeader(
            state = state,
            onAction = onAction
        )
        Column(
            modifier = Modifier
                .widthIn(max = ContentMaxWidth)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is PlaylistScreenState.Error -> Text(
                    modifier = Modifier.fillMaxSize(),
                    text = stringResource(state.messageRes),
                    textAlign = TextAlign.Center
                )

                PlaylistScreenState.Loading -> CircularProgressIndicator()
                is PlaylistScreenState.Content -> {
                    PlaylistContent(
                        modifier = Modifier.fillMaxSize(),
                        playlist = state.playlist
                    )
                }
            }
        }
    }
}

@Composable
private fun PageHeader(
    modifier: Modifier = Modifier,
    state: PlaylistScreenState,
    onAction: (PlaylistScreenAction) -> Unit
) {
    Box(
        modifier = modifier
            .height(dimensionResource(R.dimen.playlist_detail_header_height))
            .fillMaxWidth()
    ) {
        when (state) {
            is PlaylistScreenState.Error,
            PlaylistScreenState.Loading -> {
                CenterAlignedTopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { onAction(PlaylistScreenAction.OnBackClicked) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go back",
                            )
                        }
                    },
                )
            }

            is PlaylistScreenState.Content -> {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.playlist.picture)
                        .crossfade(true)
                        .build(),
                    contentDescription = state.playlist.name,
                    contentScale = ContentScale.Crop,
                    alpha = .5f
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black,
                                )
                            )
                        )
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PaddingDefault, vertical = PaddingSmall)
                        .align(Alignment.BottomCenter),
                    text = state.playlist.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                CenterAlignedTopAppBar(
                    modifier = Modifier.align(Alignment.TopStart),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                        containerColor = Color.Transparent
                    ),
                    title = {},
                    navigationIcon = {
                        IconButton(
                            modifier = Modifier.background(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            onClick = { onAction(PlaylistScreenAction.OnBackClicked) }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go back",
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun PlaylistContent(
    modifier: Modifier = Modifier,
    playlist: PlaylistDetail,
) {
    var songPlayingUrl: String? by rememberSaveable { mutableStateOf(null) }
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(PaddingDefault)
    ) {
        items(playlist.songList) {
            val isCurrentlyPlaying = songPlayingUrl == it.previewUrl

            SongItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = PaddingSmall)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(CornerRadiusDefault)
                    )
                    .height(dimensionResource(R.dimen.sound_track_height))
                    .clickable {
                        if (isCurrentlyPlaying) {
                            exoPlayer.pause()
                            songPlayingUrl = null
                        } else {
                            exoPlayer.setMediaItem(MediaItem.fromUri(it.previewUrl))
                            exoPlayer.prepare()
                            exoPlayer.play()
                            songPlayingUrl = it.previewUrl
                        }
                    },
                song = it,
                isPlaying = isCurrentlyPlaying
            )
        }
    }
}

@Composable
private fun SongItem(
    modifier: Modifier = Modifier,
    song: Song,
    isPlaying: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = PaddingDefault)
                .background(shape = CircleShape, color = Color.DarkGray)
                .padding(PaddingSmall),
            imageVector = if (isPlaying) {
                Icons.Filled.Close
            } else {
                Icons.Filled.PlayArrow
            },
            tint = Color.White,
            contentDescription = "Play"
        )

        Column(modifier = Modifier.padding(horizontal = PaddingDefault)) {
            Text(text = song.title)
            Text(text = song.artist)
        }
    }
}

@DevicePreviews
@Composable
private fun PlaylistScreenContentPreview() {
    MoodInTunesTheme {
        PlaylistScreenContent(
            state = PlaylistScreenState.Content(
                PlaylistDetail(
                    id = 666,
                    name = "Playlist of the Beast",
                    description = ">:)",
                    trackNumber = 666,
                    duration = 666,
                    picture = "",
                    creator = "Lucifer",
                    songList = listOf(
                        Song(id = 1, title = "Tribute", artist = "Tenacious D", previewUrl = "")
                    )
                )
            ),
            onAction = {}
        )
    }
}