package com.haquanghuy.tient_assistant.data.repository.impl

import com.haquanghuy.tient_assistant.data.repository.ChatRepository
import com.haquanghuy.tient_assistant.data.service.ChatDataService
import com.haquanghuy.tient_assistant.domain.model.Message
import com.haquanghuy.tient_assistant.domain.model.MessageType
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDataService: ChatDataService,
) : ChatRepository {
    override fun getMenuServiceScenario(): Message? {
        return chatDataService.getMenuMessage()
    }

    override fun getNextScenario(messageType: MessageType): Message? {
        return chatDataService.getNextMessage(messageType)
    }
}