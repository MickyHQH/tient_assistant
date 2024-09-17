package com.haquanghuy.tient_assistant.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haquanghuy.tient_assistant.ui.theme.TientAssistantTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    val messages by viewModel.messages.collectAsState()

    LaunchedEffect(Unit) {
        delay(350)
        viewModel.initRoomChat()
    }

    fun scrollToBottom() {
        scope.launch {
            delay(500)
            scrollState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Chat") }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                    )
                }
            })
        },
    ) { pd ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(pd)
                .background(color = Color.Transparent)
                .border(width = 2.dp, color = Color.Transparent)
        ) {
            MessagesContent(
                messages = messages,
                modifier = Modifier
                    .weight(1f)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollState = scrollState,
                onEnterServicePackage = {
                    viewModel.userSendMessage(it)
                    scrollToBottom()
                },
                onEnterComingSoon = {
                    viewModel.postComingSoon(it)
                    scrollToBottom()
                },
                onEnterTargetProfit = {
                    viewModel.userSendMessage(it.toString())
                    scrollToBottom()
                },
                onGetRecommendResult = {
                    viewModel.getUserAnswer()
                }
            )
            UserInput(
                modifier = Modifier.padding(vertical = 10.dp),
                onMessageSent = {
                    viewModel.userSendMessage(it, true)
                    scrollToBottom()
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    TientAssistantTheme {
        ChatScreen()
    }
}