package net.penguin.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "songs",
    foreignKeys = [
        ForeignKey(
            entity = PlaylistLocalData::class,
            parentColumns = ["id"],
            childColumns = ["playlistId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["playlistId"])]
)
data class SongLocalData(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "playlistId") val playlistId: Long,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("artist") val artist: String,
    @ColumnInfo("previewUrl") val previewUrl: String,
)