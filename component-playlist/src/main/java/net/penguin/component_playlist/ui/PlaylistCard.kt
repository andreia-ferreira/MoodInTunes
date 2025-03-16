package net.penguin.component_playlist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import net.penguin.common_design.theme.CornerRadiusDefault
import net.penguin.common_design.theme.PaddingDefault
import net.penguin.component_playlist.R
import net.penguin.domain.entity.Playlist

@Composable
fun PlaylistCard(
    modifier: Modifier = Modifier,
    playlist: Playlist,
    onClick: (Playlist) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(playlist)
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