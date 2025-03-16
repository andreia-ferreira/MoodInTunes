package net.penguin.data.model


import com.google.gson.annotations.SerializedName

data class PlaylistSearchResult(
    @SerializedName("data")
    val data: List<PlaylistJson>,
    @SerializedName("next")
    val next: String?,
    @SerializedName("total")
    val total: Int
)