package com.haquanghuy.tient_assistant.ui.screen.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haquanghuy.tient_assistant.R
import com.haquanghuy.tient_assistant.ui.compose.CommonTextField

@Composable
fun UserInput(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onMessageSent: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    Surface(
        modifier = modifier,
        tonalElevation = 2.dp,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CommonTextField(
                modifier = Modifier.weight(1f),
                value = textState,
                onValueChange = { textState = it },
                hasOutlineBorder = true,
                enabled = enabled,
                maxLines = 5,
                hintText = {
                    Text(
                        text = "Bạn trả lời ở đây nhe",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
                    )
                },
                contentPadding = PaddingValues(
                    top = 12.dp,
                    bottom = 12.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                suffix = {
                    if (textState.text.isNotEmpty()) {
                        IconButton(onClick = {
                            textState = TextFieldValue("")
                        }, modifier = Modifier.size(22.dp)) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()})
            )
            IconButton(onClick = {
                if (enabled && textState.text.isNotBlank()) {
                    onMessageSent(textState.text)
                    textState = TextFieldValue("")
                    keyboardController?.hide()
                }
            }, enabled = enabled) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Preview
@Composable
fun UserInputPreview() {
    UserInput(onMessageSent = {})
}