package net.penguin.data.model


import com.google.gson.annotations.SerializedName

data class SongJson(
    @SerializedName("data")
    val data: List<Data>
) {
    data class Data(
        @SerializedName("id")
        val id: Long,
        @SerializedName("title")
        val title: String,
        @SerializedName("artist")
        val artist: ArtistJson,
        @SerializedName("preview")
        val preview: String,
    )
}