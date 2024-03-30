package dev.nstv.composablesheep.library.util

import androidx.compose.ui.graphics.Color

object SheepColor {
    val Gray = Color(0xFFCCCCCC)
    val Blue = Color(0xFF1976D2)
    val Green = Color(0xFF3DDC84)
    val Black = Color(0xFF191B1B)
    val Purple = Color(0xFF6200EA)
    val Magenta = Color(0xFFC51162)
    val Orange = Color(0xFFFF9800)
    val Red = Color(0xFFFF0000)
    val Eye = Color(0xFFE69B43)
    val Skin = Color(0xFF444444)
    val Fluff = Gray

    val list = listOf(
        Fluff,
        Blue,
        Green,
        Black,
        Purple,
        Magenta,
        Orange,
        Red,
    )

    fun random() = list.random()

    fun random(excludeColor: Color) = list.filter { it != excludeColor }.random()
}
