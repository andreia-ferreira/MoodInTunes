package net.penguin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CachedSearchData(
    val currentIndex: Int = 0,
    val results: List<Playlist>
) {
    @Serializable
    data class Playlist(
        val isSaved: Boolean,
        val data: PlaylistSearchResult.Playlist
    )
}