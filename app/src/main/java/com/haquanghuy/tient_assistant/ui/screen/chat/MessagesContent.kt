package com.haquanghuy.tient_assistant.ui.screen.chat

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haquanghuy.tient_assistant.domain.model.listService
import com.haquanghuy.tient_assistant.domain.model.Message
import com.haquanghuy.tient_assistant.domain.model.MessageType
import com.haquanghuy.tient_assistant.domain.model.UserAnswer
import com.haquanghuy.tient_assistant.ui.compose.CommonRadioButton
import com.haquanghuy.tient_assistant.ui.compose.MrTient
import com.haquanghuy.tient_assistant.ui.theme.TientAssistantTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MessagesContent(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    scrollState: LazyListState,
    onEnterServicePackage: (String) -> Unit = {},
    onEnterComingSoon: (String) -> Unit = {},
    onEnterTargetProfit: (Double) -> Unit = {},
    onGetRecommendResult: () -> UserAnswer,
) {
    Box(modifier = modifier) {
        LazyColumn(
//            reverseLayout = true,
            state = scrollState,
            modifier = Modifier.fillMaxSize()
        ) {
            for (index in messages.indices) {
                val prevAuthor = messages.getOrNull(index + 1)?.authorId
                val nextAuthor = messages.getOrNull(index - 1)?.authorId
                val content = messages[index]
                Log.d("HUYUYU", "prevAuthor $prevAuthor nextAuthor $nextAuthor")
                val isFirstMessageByAuthor = prevAuthor != content.authorId
                val isLastMessageByAuthor = nextAuthor != content.authorId

                item {
                    Message(
                        msg = content,
                        isUserMe = content.authorId == Message.USER_ID,
                        isFirstMessageByAuthor = isFirstMessageByAuthor,
                        isLastMessageByAuthor = isLastMessageByAuthor,
                        onEnterServicePackage = onEnterServicePackage,
                        onEnterComingSoon = onEnterComingSoon,
                        onEnterTargetProfit = onEnterTargetProfit,
                        onGetRecommendResult = onGetRecommendResult,
                    )
                }
            }
        }
    }
}

@Composable
fun Message(
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    onEnterServicePackage: (String) -> Unit = {},
    onEnterComingSoon: (String) -> Unit = {},
    onEnterTargetProfit: (Double) -> Unit = {},
    onGetRecommendResult: () -> UserAnswer,
) {

    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Box(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(.88f)
                .align(if (isUserMe) Alignment.TopEnd else Alignment.TopStart),
        ) {
            if (!isUserMe) {
                if (isFirstMessageByAuthor) {
                    MrTient(100)
                } else {
                    // Space under avatar
                    Spacer(modifier = Modifier.width(100.dp))
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = if (isUserMe) Alignment.End else Alignment.Start
            ) {
                if (isLastMessageByAuthor) {
                    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
                        Text(
                            text = msg.authorName,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .alignBy(LastBaseline)
                                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Column {
                    Surface(
                        color = backgroundBubbleColor,
                    ) {
                        Text(
                            text = msg.content,
                            style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                    MessageAttachments(
                        msg,
                        onEnterServicePackage = onEnterServicePackage,
                        onEnterComingSoon = onEnterComingSoon,
                        onEnterTargetProfit = onEnterTargetProfit,
                        onGetRecommendResult = onGetRecommendResult,
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

@Composable
fun MessageAttachments(
    msg: Message,
    onEnterServicePackage: (String) -> Unit = {},
    onEnterComingSoon: (String) -> Unit = {},
    onEnterTargetProfit: (Double) -> Unit = {},
    onGetRecommendResult: () -> UserAnswer,
) {
    when (msg.messageType) {
        MessageType.MENU_SERVICE -> {
            MenuService(
                onEnterServicePackage = onEnterServicePackage,
                onEnterComingSoon = onEnterComingSoon,
            )
        }
        MessageType.LIST_PRODUCT -> {
            ListProduct()
        }
        MessageType.TARGET_PROFIT -> {
            TargetProfit(
                onOptionSelected = onEnterTargetProfit
            )
        }
        MessageType.RESULT -> {
            RecommendResult(
                onGetRecommendResult = onGetRecommendResult,
            )
        }

        else -> {

        }
    }
}

@Composable
fun MenuService(
    onEnterServicePackage: (String) -> Unit = {},
    onEnterComingSoon: (String) -> Unit = {},
) {
    val listService = listOf(
        "Tư vấn các gói dịch vụ" to onEnterServicePackage,
        "Đăng ký khách hàng mới" to onEnterComingSoon,
//        "Chat cùng tư vấn viên" to onEnterComingSoon
    )
    Column(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
            .padding(horizontal = 10.dp),
    ) {
        listService.forEachIndexed { index, value ->
            Text(modifier = Modifier
                .fillMaxWidth()
                .clickable { value.second(value.first) }
                .padding(vertical = 10.dp), text = value.first)
            if (listService.size - 1 != index) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ListProduct() {
    Surface(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .7f)) {
        Column(
            modifier = Modifier
                .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                .padding(horizontal = 10.dp),
        ) {
            listService.forEachIndexed { index, value ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp), text = "${value.name}, ${value.intro}"
                )
                if (listService.size - 1 != index) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun TargetProfit(
    onOptionSelected: (Double) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var selectedOption by remember {
        mutableDoubleStateOf(listService.first().value)
    }
    Column(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
            .padding(horizontal = 10.dp),
    ) {
        listService.forEach { option ->
            CommonRadioButton(
                value = option.intro,
                selected = option.value == selectedOption,
            ) {
                scope.launch {
                    selectedOption = option.value
                    delay(200)
                    onOptionSelected(option.value)
                }
            }
        }
    }
}

@Composable
fun RecommendResult(
    onGetRecommendResult: () -> UserAnswer,
) {
    val userAnswer = onGetRecommendResult()
    val serviceChose = listService.find { it.value == userAnswer.target }
    Box(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
            .padding(horizontal = 10.dp),
    ) {
        if (serviceChose == null) {
            Text(text = "No data to show")
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp), text = "${serviceChose.name}, ${serviceChose.intro}"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuServicePreview() {
    TientAssistantTheme {
        MenuService()
    }
}