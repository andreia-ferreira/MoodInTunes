package net.penguin.moodintunes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.penguin.domain.repository.CollectionRepository
import net.penguin.domain.repository.SearchRepository
import net.penguin.domain.usecase.GetPlaylistDetailUseCase
import net.penguin.domain.usecase.SearchByMoodUseCase

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSearchByMoodUseCase(repository: SearchRepository): SearchByMoodUseCase {
        return SearchByMoodUseCase(repository)
    }

    @Provides
    fun provideGetPlaylistDetailUseCase(searchRepository: SearchRepository, collectionRepository: CollectionRepository): GetPlaylistDetailUseCase {
        return GetPlaylistDetailUseCase(searchRepository, collectionRepository)
    }
}