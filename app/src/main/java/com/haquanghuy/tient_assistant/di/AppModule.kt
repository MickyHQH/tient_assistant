package com.haquanghuy.tient_assistant.di

import com.haquanghuy.tient_assistant.data.service.ChatDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProviderModule {
    @Provides
    @Singleton
    fun provideGlobalScope(): CoroutineScope = MainScope()

    @Provides
    @Singleton
    fun provideChatDataService(): ChatDataService = ChatDataService()
}
