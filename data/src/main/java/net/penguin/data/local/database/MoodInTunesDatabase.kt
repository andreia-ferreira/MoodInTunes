package net.penguin.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import net.penguin.data.local.database.entity.PlaylistLocalData
import net.penguin.data.local.database.entity.SongLocalData

@Database(entities = [PlaylistLocalData::class, SongLocalData::class], version = 1)
abstract class MoodInTunesDatabase: RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
}