package com.haquanghuy.tient_assistant.domain.model

data class Message(
    val authorId: String,
    val authorName: String,
    val content: String,
    val answerTarget: String? = null,
    val timestamp: Int,
    val messageType: MessageType = MessageType.NONE,
    val isSkip: Boolean = false,
    val enableTyping: Boolean = false,
) {
    companion object {
        const val BOT_ID = "BOT_ID"
        const val USER_ID = "USER_ID"
        const val DOCS_ID = "DOCS_ID"
    }
}

enum class MessageType {
    NONE, NAME, MENU_SERVICE, LIST_PRODUCT, TARGET_PROFIT, RESULT
}