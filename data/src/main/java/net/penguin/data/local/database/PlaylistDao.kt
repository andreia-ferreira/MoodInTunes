package net.penguin.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import net.penguin.data.local.database.entity.PlaylistLocalData
import net.penguin.data.local.database.entity.PlaylistWithSongs
import net.penguin.data.local.database.entity.SongLocalData

@Dao
interface PlaylistDao {
    @Transaction
    @Query("SELECT * FROM playlists")
    fun getAllPlaylistsWithSongs(): Flow<List<PlaylistWithSongs>>

    @Transaction
    @Query("SELECT * FROM playlists WHERE id = :playlistId")
    suspend fun getPlaylistWithSongs(playlistId: Long): PlaylistWithSongs

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistLocalData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songs: List<SongLocalData>)

    @Transaction
    suspend fun insertPlaylistWithSongs(playlist: PlaylistLocalData, songs: List<SongLocalData>) {
        insertPlaylist(playlist)
        insertSongs(songs)
    }

    @Update
    suspend fun updatePlaylist(playlist: PlaylistLocalData)

    @Query("DELETE FROM playlists WHERE id = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)
}