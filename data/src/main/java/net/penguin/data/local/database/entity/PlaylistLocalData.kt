package net.penguin.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistLocalData(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "creator") val creator: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "duration") val duration: Long,
    @ColumnInfo(name = "trackNumber") val trackNumber: Int,
    @ColumnInfo(name = "pictureUrl") val pictureUrl: String,
)