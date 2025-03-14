package net.penguin.data.remote

import net.penguin.data.model.PlaylistDetailResult
import net.penguin.data.model.PlaylistSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {
    @GET("search/playlist?public=true&order=RATING_DESC")
    suspend fun searchPlaylist(@Query("q") query: String): Response<PlaylistSearchResult>
    @GET("playlist")
    suspend fun getPlaylistDetails(@Path("id") id: Int): Response<PlaylistDetailResult>
}