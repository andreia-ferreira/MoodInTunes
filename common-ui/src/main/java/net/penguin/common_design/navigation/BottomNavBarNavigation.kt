package net.penguin.common_design.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavBarNavigation {
    @Serializable
    data object Discover: BottomNavBarNavigation()
    @Serializable
    data object Collection: BottomNavBarNavigation()
}

@Serializable
data class PlaylistDetailNavigation(val playlistId: Long)