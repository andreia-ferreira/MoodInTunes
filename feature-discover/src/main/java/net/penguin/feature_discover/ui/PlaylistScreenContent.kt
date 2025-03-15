package net.penguin.feature_discover.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.penguin.common_design.DevicePreviews
import net.penguin.common_design.theme.MoodInTunesTheme
import net.penguin.domain.entity.PlaylistDetail
import net.penguin.domain.entity.Song
import net.penguin.feature_discover.model.PlaylistScreenState

@Composable
fun PlaylistScreenContent(
    modifier: Modifier = Modifier,
    state: PlaylistScreenState
) {
    Column(modifier = modifier) {
        Text("hello")
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
                        Song(id = 1, title = "Tribute", artist = "Tenacious D")
                    )
                )
            )
        )
    }
}