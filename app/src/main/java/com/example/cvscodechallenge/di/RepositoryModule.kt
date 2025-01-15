package com.example.cvscodechallenge.di

import com.example.cvscodechallenge.data.repository.Repository
import com.example.cvscodechallenge.domain.repository.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRepository(repository: Repository): IRepository
}