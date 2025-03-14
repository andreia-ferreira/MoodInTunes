package net.penguin.data.model


import com.google.gson.annotations.SerializedName

data class ArtistJson(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)