package com.haquanghuy.tient_assistant.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haquanghuy.tient_assistant.ui.utils.addIf

@Composable
fun CommonTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    suffix: @Composable () -> Unit = {},
    hintText: @Composable () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(8.dp),
    hasUnderline: Boolean = false,
    hasOutlineBorder: Boolean = false,
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = MaterialTheme.colorScheme.onSurface
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    val primaryColor = MaterialTheme.colorScheme.primary

    BasicTextField(
        value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(mergedTextStyle.color),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        decorationBox = { innerTextField ->
            Row(
                modifier = modifier
                    .addIf(!enabled) {
                        background(Color.Gray.copy(alpha = .3f))
                    }
                    .addIf(hasOutlineBorder) {
                        border(1.dp, Color.Gray, RoundedCornerShape(10))
                    }
                    .padding(contentPadding)
                    .addIf(hasUnderline) {
                        drawBehind {
                            val strokeWidthPx = 2.dp.toPx()
                            val verticalOffset = size.height - 2.sp.toPx()
                            drawLine(
                                color = primaryColor,
                                strokeWidth = strokeWidthPx,
                                start = Offset(0f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        }
                    },
            ) {
                Box(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f),
                ) {
                    innerTextField()

                    if (value.text.isEmpty()) {
                        hintText()
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                suffix()
            }
        },
    )
}