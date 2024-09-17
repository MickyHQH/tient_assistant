package com.haquanghuy.tient_assistant.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haquanghuy.tient_assistant.R
import com.haquanghuy.tient_assistant.ui.compose.MrTient
import com.haquanghuy.tient_assistant.ui.theme.TientAssistantTheme

@Composable
fun HomeScreen(
    gotoChat: () -> Unit = {}
) {
    Scaffold { pd ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "HHH Finance",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painterResource(R.drawable.ic_hhh),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(200.dp)
                        .wrapContentHeight(),
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "Chào mừng bạn đến với trợ lý ảo của HHH Finance. HHH Finance cung cấp nhiều gói dịch vụ gửi tiết kiệm với đa dạng lựa chọn cho khách hàng.\n\nTrợ lý ảo Tient sẽ hỗ trợ bạn lựa chọn gói dịch vụ phù hợp nhất với khẩu vị đầu tư tích luỹ của bạn.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                MrTient()
            }
            FilledTonalButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp),
                onClick = gotoChat
            ) {
                Text(text = "Bắt đầu nói chuyện với Tient")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TientAssistantTheme {
        HomeScreen()
    }
}