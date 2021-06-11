package com.shindejayesharun.moviesapptechbulls.di

import com.shindejayesharun.moviesapptechbulls.data.error.mapper.ErrorMapper
import com.shindejayesharun.moviesapptechbulls.data.error.mapper.ErrorMapperSource
import com.shindejayesharun.moviesapptechbulls.usecase.errors.ErrorUseCase
import com.shindejayesharun.moviesapptechbulls.usecase.errors.ErrorManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// with @Module we Telling Dagger that, this is a Dagger module
@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}
