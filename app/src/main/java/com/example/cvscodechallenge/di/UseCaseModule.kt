package com.example.cvscodechallenge.di

import com.example.cvscodechallenge.domain.repository.IFlickrRepository
import com.example.cvscodechallenge.domain.useCase.GetFlickPhotoFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * UseCase module binds the `GetFlickPhotoFeedUseCase` to its corresponding `IFlickrRepository` dependency,
 * ensuring that a singleton instance of the use case is provided throughout the app's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetFlickrPhotoFeedUseCase(
        repository: IFlickrRepository
    ): GetFlickPhotoFeedUseCase {
        return GetFlickPhotoFeedUseCase(repository)
    }
}