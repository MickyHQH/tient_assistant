package com.haquanghuy.tient_assistant.ui.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haquanghuy.tient_assistant.ui.theme.TientAssistantTheme

@Composable
fun CommonRadioButton(
    modifier: Modifier = Modifier,
    value: String,
    selected: Boolean,
    enabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit) = {},
) {
    Row(
        modifier.selectable(
                selected = selected,
                onClick = {
                    onClick()
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource,
            onClick = { onClick() }
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.merge(),
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommonRadioButtonPreview() {
    TientAssistantTheme {
        CommonRadioButton(value = "Private", selected = true)
    }
}