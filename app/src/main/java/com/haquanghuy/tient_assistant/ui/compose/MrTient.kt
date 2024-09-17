package com.haquanghuy.tient_assistant.ui.compose

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.haquanghuy.tient_assistant.R
import com.haquanghuy.tient_assistant.ui.theme.TientAssistantTheme

@Composable
fun MrTient(size: Int = 230) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_tient))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    LottieAnimation(
        modifier = Modifier.size(size.dp),
        composition = composition,
        progress = { progress },
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TientAssistantTheme {
        MrTient()
    }
}