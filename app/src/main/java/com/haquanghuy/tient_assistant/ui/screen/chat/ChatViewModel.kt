package com.haquanghuy.tient_assistant.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haquanghuy.tient_assistant.data.repository.ChatRepository
import com.haquanghuy.tient_assistant.domain.model.Message
import com.haquanghuy.tient_assistant.domain.model.MessageType
import com.haquanghuy.tient_assistant.domain.model.UserAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    private var currentMessageType = MessageType.NONE
    private var userAnswer: UserAnswer = UserAnswer()
    private val delayTime = 300L

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>>
        get() = _messages.asStateFlow()

    private fun botSendMessage(message: String) {
        _messages.value += Message(
            authorId = Message.BOT_ID,
            authorName = "Tient",
            content = message,
            timestamp = Calendar.getInstance().timeInMillis.toInt(),
        )
    }

    fun getUserAnswer() = userAnswer

    fun postComingSoon(message: String) {
        viewModelScope.launch {
            _messages.value += Message(
                authorId = Message.USER_ID,
                authorName = "Tôi",
                content = message,
                timestamp = Calendar.getInstance().timeInMillis.toInt(),
            )
            delay(delayTime)
            botSendMessage("Thông tin của bạn đã được hệ thống ghi nhận, bạn vui lòng đến chi nhánh gần nhất để được hỗ trợ nhé. \nBạn còn muốn được hỗ trợ gì nữa không?")
            chatRepository.getMenuServiceScenario()?.also {
                _messages.value += it.copy(authorName = "Tient", authorId = Message.BOT_ID)
                currentMessageType = it.messageType
            }
        }
    }

    fun userSendMessage(message: String, fromTyping: Boolean = false) {
        viewModelScope.launch {
            val currentMessage = _messages.value.lastOrNull()
            _messages.value += Message(
                authorId = Message.USER_ID,
                authorName = "Tôi",
                content = message,
                timestamp = Calendar.getInstance().timeInMillis.toInt(),
            )
            if (fromTyping && currentMessage?.enableTyping != true) {
                botSendMessage("Khúc này bạn ${userAnswer.name} cần chọn trên giao diện, không cần phải nhập liệu nhé. Để mình gửi lại bảng câu hỏi.")
                delay(delayTime)
                if (currentMessage != null) {
                    _messages.value += currentMessage
                }
                return@launch
            }
            when (currentMessage?.messageType) {
                MessageType.NAME -> {
                    userAnswer = userAnswer.copy(name = message)
                }
                MessageType.TARGET_PROFIT -> {
                    userAnswer = userAnswer.copy(target = message.toDouble())
                }
                else -> {}
            }
            delay(delayTime)
            botSendMessage(String.format(currentMessage?.answerTarget ?: "", message))
            delay(delayTime)
            getNextScenario(currentMessageType)
        }
    }

    fun initRoomChat() {
        _messages.value = emptyList()
        getNextScenario(MessageType.NONE)
    }

    private fun getNextScenario(messageType: MessageType) {
        viewModelScope.launch {
            var nextMessage: Message? = null
            var type = messageType
            do {
                delay(delayTime)
                chatRepository.getNextScenario(type)?.also {
                    _messages.value += it.copy(authorName = "Tient", authorId = Message.BOT_ID)
                    currentMessageType = it.messageType
                    nextMessage = it
                    type = it.messageType
                }
            } while (nextMessage?.isSkip == true)
        }
    }
}