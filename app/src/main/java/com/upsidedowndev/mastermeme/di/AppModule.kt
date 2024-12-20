package com.upsidedowndev.mastermeme.di

import android.content.Context
import com.upsidedowndev.mastermeme.meme.data.repository.RepositoryImpl
import com.upsidedowndev.mastermeme.meme.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRepository(
        @ApplicationContext context: Context
    ): Repository {
        return RepositoryImpl(context)
    }
}