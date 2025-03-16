package net.penguin.data.local

import kotlinx.serialization.Serializable
import net.penguin.data.model.PlaylistJson

@Serializable
data class CachedSearchData(
    val currentIndex: Int = 0,
    val results: List<PlaylistJson>
)