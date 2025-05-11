package com.example.composejoyride.ui.theme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatClear
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState

@Composable
fun RichTextFormattingToolbar(state: RichTextState) {
    val activeStyle = state.currentSpanStyle
    val backgroundColor = MaterialTheme.colorScheme.secondary
    val activeModifier = Modifier.background(
        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        CircleShape
    )

    var colorMenuExpanded by remember { mutableStateOf(false) }

    val highlightColors = listOf(
        Color.Yellow to "Жёлтый",
        Color.Cyan to "Голубой",
        Color.LightGray to "Серый",
        Color.Red to "Красный",
        Color.Magenta to "Розовый"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(backgroundColor, RoundedCornerShape(12.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
            },
            modifier = if (activeStyle.fontWeight == FontWeight.Bold) activeModifier else Modifier
        ) {
            Icon(Icons.Default.FormatBold, contentDescription = "Bold")
        }

        IconButton(
            onClick = {
                state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
            },
            modifier = if (activeStyle.fontStyle == FontStyle.Italic) activeModifier else Modifier
        ) {
            Icon(Icons.Default.FormatItalic, contentDescription = "Italic")
        }

        IconButton(
            onClick = {
                state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
            },
            modifier = if (activeStyle.textDecoration == TextDecoration.Underline) activeModifier else Modifier
        ) {
            Icon(Icons.Default.FormatUnderlined, contentDescription = "Underline")
        }

        // Цветной маркер (выпадающее меню)
        Box {
            IconButton(
                onClick = { colorMenuExpanded = true },
                modifier = if (highlightColors.any { it.first == activeStyle.background }) activeModifier else Modifier
            ) {
                Icon(Icons.Default.ColorLens, contentDescription = "Выделить маркером")
            }

            DropdownMenu(
                expanded = colorMenuExpanded,
                onDismissRequest = { colorMenuExpanded = false }
            ) {
                highlightColors.forEach { (color, name) ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(color, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(name)
                            }
                        },
                        onClick = {
                            state.toggleSpanStyle(SpanStyle(background = color))
                            colorMenuExpanded = false
                        }
                    )
                }
            }
        }

        // Сбросить все стили
        IconButton(onClick = {
            state.removeSpanStyle(state.currentSpanStyle)
        }) {
            Icon(Icons.Default.FormatClear, contentDescription = "Очистить стили")
        }
    }
}
