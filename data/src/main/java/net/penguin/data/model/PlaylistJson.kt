package net.penguin.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistJson(
    @SerializedName("id")
    val id: Long,
    @SerializedName("nb_tracks")
    val nbTracks: Int,
    @SerializedName("picture_medium")
    val pictureMedium: String,
    @SerializedName("title")
    val title: String,
)