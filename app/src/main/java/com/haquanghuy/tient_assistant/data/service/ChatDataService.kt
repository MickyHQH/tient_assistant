package com.haquanghuy.tient_assistant.data.service

import com.haquanghuy.tient_assistant.domain.model.Message
import com.haquanghuy.tient_assistant.domain.model.MessageType
import java.util.Calendar

class ChatDataService {
    private val scenario = listOf(
        Message(
            authorId = Message.DOCS_ID,
            authorName = "",
            content = "Hello! Chào một ngày tốt lành!\n" +
                    "Để bắt đầu cuộc trò chuyện, cho Tient hỏi bạn tên là gì?",
            answerTarget = "Xin chào %s nhe",
            timestamp = Calendar.getInstance().timeInMillis.toInt(),
            messageType = MessageType.NAME,
            enableTyping = true,
        ),
        Message(
            authorId = Message.DOCS_ID,
            authorName = "",
            content = "Dưới đây là danh sách các dịch vụ, bạn đang quan tâm đến dịch vụ nào?",
            timestamp = Calendar.getInstance().timeInMillis.toInt(),
            answerTarget = "Ok để tôi xem nào.",
            messageType = MessageType.MENU_SERVICE
        ),
        Message(
            authorId = Message.DOCS_ID,
            authorName = "",
            content = "Hiện tại chúng tôi cung cấp các gói dịch vụ như sau: ",
            timestamp = Calendar.getInstance().timeInMillis.toInt(),
            messageType = MessageType.LIST_PRODUCT,
            isSkip = true,
        ),
        Message(
            authorId = Message.DOCS_ID,
            authorName = "",
            content = "Để rõ hơn về nhu cầu của bạn, cho tôi biết mức lợi nhuận mong muốn hàng năm của bạn?",
            answerTarget = "Tuyệt vời!",
            timestamp = Calendar.getInstance().timeInMillis.toInt(),
            messageType = MessageType.TARGET_PROFIT
        ),
        Message(
            authorId = Message.DOCS_ID,
            authorName = "",
            content = "Cám ơn sự hợp tác của bạn. Qua những chia sẻ của bạn, Tient đã tìm ra gói dịch vụ phù hợp với bạn. Bạn xem tham khảo nhé!",
            timestamp = Calendar.getInstance().timeInMillis.toInt(),
            messageType = MessageType.RESULT
        ),
    )

    fun getNextMessage(messageType: MessageType): Message? {
        if (MessageType.RESULT == messageType) return null
        return scenario[scenario.indexOfFirst { it.messageType == messageType } + 1]
    }

    fun getMenuMessage(): Message? {
        return scenario.firstOrNull { it.messageType == MessageType.MENU_SERVICE }
    }
}
