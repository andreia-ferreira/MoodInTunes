@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package net.penguin.feature_discover.ui

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
import net.penguin.common_design.theme.CornerRadiusDefault
import net.penguin.common_design.theme.MoodInTunesTheme
import net.penguin.common_design.theme.PaddingDefault
import net.penguin.common_design.theme.PaddingSmall
import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.entity.Song
import net.penguin.feature_discover.model.PlaylistScreenState

@Composable
fun PlaylistScreenContent(
    modifier: Modifier = Modifier,
    state: PlaylistScreenState,
    onAction: (PlaylistScreenAction) -> Unit
) {
    Box(modifier = modifier) {
        CenterAlignedTopAppBar(
            title = {},
            navigationIcon = {
                IconButton(
                    modifier = Modifier.background(shape = CircleShape, color = Color.Gray),
                    onClick = { onAction(PlaylistScreenAction.OnBackClicked) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back",
                        tint = Color.White
                    )
                }
            },
        )

        when (state) {
            is PlaylistScreenState.Error -> Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(state.messageRes),
                textAlign = TextAlign.Center
            )
            PlaylistScreenState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
            is PlaylistScreenState.Content -> {
                PlaylistContent(
                    playlist = state.playlist,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun PlaylistContent(
    modifier: Modifier = Modifier,
    playlist: PlaylistDetail,
    onAction: (PlaylistScreenAction) -> Unit
) {
    var songPlayingUrl: String? by rememberSaveable { mutableStateOf(null) }
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Column(modifier) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(playlist.picture)
                    .crossfade(true)
                    .build(),
                contentDescription = playlist.name,
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
                text = playlist.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(
            modifier = Modifier
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
                        .height(100.dp)
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