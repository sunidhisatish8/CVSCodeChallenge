package com.example.cvscodechallenge.di

import com.example.cvscodechallenge.domain.repository.IRepository
import com.example.cvscodechallenge.domain.useCase.GetFlickPhotoFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetFlickrPhotoFeedUseCase(
        repository: IRepository
    ): GetFlickPhotoFeedUseCase {
        return GetFlickPhotoFeedUseCase(repository)
    }
}