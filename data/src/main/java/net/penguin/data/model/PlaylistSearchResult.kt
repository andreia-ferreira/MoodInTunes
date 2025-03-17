package net.penguin.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class PlaylistSearchResult(
    @SerializedName("data")
    val data: List<Playlist>,
    @SerializedName("next")
    val next: String?,
    @SerializedName("total")
    val total: Int
) {
    @Serializable
    data class Playlist(
        @SerializedName("id")
        val id: Long,
        @SerializedName("nb_tracks")
        val nbTracks: Int,
        @SerializedName("picture_medium")
        val pictureMedium: String,
        @SerializedName("title")
        val title: String
    )
}