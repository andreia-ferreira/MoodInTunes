package net.penguin.feature_discover.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationItem {
    @Serializable
    data object Discover: NavigationItem()
    @Serializable
    data class PlaylistDetail(val playlistId: Long): NavigationItem()
}