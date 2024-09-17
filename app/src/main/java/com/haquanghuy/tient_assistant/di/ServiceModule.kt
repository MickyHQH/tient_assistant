package com.haquanghuy.tient_assistant.di

import com.haquanghuy.tient_assistant.data.repository.ChatRepository
import com.haquanghuy.tient_assistant.data.repository.impl.ChatRepositoryImpl
import com.haquanghuy.tient_assistant.data.service.ChatDataService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
  @Binds abstract fun provideChatRepository(impl: ChatRepositoryImpl): ChatRepository
}
