package net.penguin.domain.entity

data class Playlist(
    val id: Long,
    val name: String,
    val trackNumber: Int,
    val thumbnail: String,
    val isSaved: Boolean
)