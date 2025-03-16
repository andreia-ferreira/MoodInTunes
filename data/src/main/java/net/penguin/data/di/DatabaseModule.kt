package net.penguin.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.penguin.data.local.database.MoodInTunesDatabase
import net.penguin.data.local.database.PlaylistDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MoodInTunesDatabase {
        return Room.databaseBuilder(
            appContext,
            MoodInTunesDatabase::class.java,
            "mood_in_tunes"
        ).build()
    }

    @Provides
    fun provideMyDao(database: MoodInTunesDatabase): PlaylistDao {
        return database.playlistDao()
    }
}