package net.penguin.domain.entity

data class PlaylistDetail(
    val id: Long,
    val isSaved: Boolean,
    val name: String,
    val description: String,
    val trackNumber: Int,
    val duration: Long,
    val picture: String,
    val creator: String,
    val songList: List<Song>
)