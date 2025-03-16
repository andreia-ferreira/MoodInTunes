package net.penguin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CachedSearchData(
    val currentIndex: Int = 0,
    val results: List<PlaylistJson>
)