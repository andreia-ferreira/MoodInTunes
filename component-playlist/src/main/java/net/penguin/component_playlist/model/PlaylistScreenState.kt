package net.penguin.component_playlist.model

import androidx.annotation.StringRes
import net.penguin.domain.entity.PlaylistDetail

sealed class PlaylistScreenState {
    data object Loading: PlaylistScreenState()
    data class Error(@StringRes val messageRes: Int): PlaylistScreenState()
    data class Content(val playlist: PlaylistDetail): PlaylistScreenState()
}