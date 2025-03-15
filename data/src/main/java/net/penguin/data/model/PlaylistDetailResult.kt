package net.penguin.data.model


import com.google.gson.annotations.SerializedName

data class PlaylistDetailResult(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("creator")
    val creator: Creator,
    @SerializedName("description")
    val description: String,
    @SerializedName("duration")
    val duration: Long,
    @SerializedName("nb_tracks")
    val nbTracks: Int,
    @SerializedName("picture_big")
    val pictureBig: String,
    @SerializedName("tracks")
    val tracks: SongJson,
) {
    data class Creator(
        @SerializedName("name")
        val name: String,
    )
}