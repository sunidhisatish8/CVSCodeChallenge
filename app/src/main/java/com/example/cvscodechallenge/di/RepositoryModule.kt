package com.example.cvscodechallenge.di

import com.example.cvscodechallenge.data.repository.FlickrRepository
import com.example.cvscodechallenge.domain.repository.IFlickrRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Repository module that binds the implementation of the repository interface to its class
 * This module provides the `IFlickrRepository` dependency as a singleton instance
*/
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRepository(flickrRepository: FlickrRepository): IFlickrRepository
}