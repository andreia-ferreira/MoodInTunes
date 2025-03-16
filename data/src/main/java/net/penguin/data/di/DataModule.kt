package net.penguin.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.penguin.data.CollectionRepositoryImpl
import net.penguin.data.SearchRepositoryImpl
import net.penguin.domain.repository.CollectionRepository
import net.penguin.domain.repository.SearchRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    fun bindCollectionRepository(collectionRepositoryImpl: CollectionRepositoryImpl): CollectionRepository
}