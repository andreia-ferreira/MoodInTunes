package net.penguin.data.model


import com.google.gson.annotations.SerializedName

data class PlaylistSearchResult(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("next")
    val next: String?,
    @SerializedName("total")
    val total: Int
) {
    data class Data(
        @SerializedName("id")
        val id: Long,
        @SerializedName("nb_tracks")
        val nbTracks: Int,
        @SerializedName("picture_medium")
        val pictureMedium: String,
        @SerializedName("title")
        val title: String,
    )
}