package com.example.composejoyride.ui.theme.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.composejoyride.ui.theme.TheFont

@Composable
fun InfoRow(label: String, value: String, color: Color) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "$label:",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = color,
            fontFamily = TheFont
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = color,
            fontFamily = TheFont
        )
    }
}
