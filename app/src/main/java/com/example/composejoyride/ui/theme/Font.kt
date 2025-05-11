package com.example.composejoyride.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.composejoyride.R

val SchistFont = FontFamily(
    Font(R.font.schist_regular, FontWeight.Normal)
)

val TippyToesFont = FontFamily(
    Font(R.font.tippytoes, FontWeight.Normal)
)

val CormorantFont = FontFamily(
    Font(R.font.cormorant_garamond, FontWeight.Normal)
)

val LoraFont = FontFamily(
    Font(R.font.lora, FontWeight.Normal)
)

val EBGaramondFont = FontFamily(
    Font(R.font.ebgaramond, FontWeight.Normal)
)

val RobotoFont = FontFamily(
    Font(R.font.roboto, FontWeight.Normal)
)

var TheFont = RobotoFont

val fontOptions = listOf(
    "Roboto" to RobotoFont,
    "Schist" to SchistFont,
    "TippyToes" to TippyToesFont,
    "Cormorant" to CormorantFont,
    "Lora" to LoraFont,
    "Tangerine" to EBGaramondFont
)