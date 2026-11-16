package com.example.schulnotizen.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val OledBlack = Color(0xFF000000)
val SamsungSurface = Color(0xFF111116)
val SamsungSurfaceVariant = Color(0xFF1B1B22)
val SamsungBlue = Color(0xFF0A84FF)
val AccentPurple = Color(0xFFBF5AF2)

private val SchoolDarkColorScheme = darkColorScheme(
    primary = SamsungBlue,
    onPrimary = Color.White,
    secondary = AccentPurple,
    onSecondary = Color.White,
    background = OledBlack,
    onBackground = Color.White,
    surface = SamsungSurface,
    onSurface = Color.White,
    surfaceVariant = SamsungSurfaceVariant,
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = Color(0xFF2A2A36)
)

@Composable
fun SchulNotizenTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SchoolDarkColorScheme,
        content = content
    )
}
