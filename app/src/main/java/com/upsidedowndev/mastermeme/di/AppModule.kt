package com.upsidedowndev.mastermeme.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.upsidedowndev.mastermeme.meme.data.dao.MemeDao
import com.upsidedowndev.mastermeme.meme.data.database.MemeDatabase
import com.upsidedowndev.mastermeme.meme.data.repository.RepositoryImpl
import com.upsidedowndev.mastermeme.meme.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MemeDatabase {
        return Room.databaseBuilder(
            context,
            MemeDatabase::class.java,
            "meme_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMemeDao(
        database: MemeDatabase
    ): MemeDao {
        return database.memeDao()
    }

    @Provides
    @Singleton
    fun provideRepository(
        @ApplicationContext context: Context,
        memeDao: MemeDao
    ): Repository {
        return RepositoryImpl(context,memeDao)
    }
}