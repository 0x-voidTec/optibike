package com.optibike.fitting.di

import android.content.Context
import android.content.SharedPreferences
import com.optibike.fitting.data.repository.SettingsRepositoryImpl
import com.optibike.fitting.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Settings Module for Hilt Dependency Injection
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("OptiBikeSettings", Context.MODE_PRIVATE)
    }
    
    @Provides
    @Singleton
    fun provideSettingsRepository(
        sharedPreferences: SharedPreferences
    ): SettingsRepository {
        return SettingsRepositoryImpl(sharedPreferences)
    }
}
