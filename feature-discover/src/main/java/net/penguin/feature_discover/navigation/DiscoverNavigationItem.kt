package net.penguin.feature_discover.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class DiscoverNavigationItem {
    @Serializable
    data class PlaylistDetail(val playlistId: Long): DiscoverNavigationItem()
}