package net.penguin.domain.entity

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val previewUrl: String
)