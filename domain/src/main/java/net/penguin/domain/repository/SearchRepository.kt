package net.penguin.domain.repository

import net.penguin.domain.entity.Playlist

interface SearchRepository {
    suspend fun searchPlaylists(query: String): Result<List<Playlist>>
}