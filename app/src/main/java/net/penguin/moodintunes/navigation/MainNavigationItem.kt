package net.penguin.moodintunes.navigation

import kotlinx.serialization.Serializable


sealed class MainNavigationItem {
    @Serializable
    data object Discover: MainNavigationItem()
    @Serializable
    data object Collection: MainNavigationItem()
}