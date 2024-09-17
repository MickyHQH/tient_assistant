package com.haquanghuy.tient_assistant.data.repository

import com.haquanghuy.tient_assistant.domain.model.Message
import com.haquanghuy.tient_assistant.domain.model.MessageType

interface ChatRepository {
    fun getMenuServiceScenario(): Message?
    fun getNextScenario(messageType: MessageType): Message?
}