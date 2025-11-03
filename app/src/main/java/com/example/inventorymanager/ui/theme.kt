package com.example.inventorymanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF007B55),
    onPrimary = Color.White,
    secondary = Color(0xFFFFA726),
    onSecondary = Color.White,
    background = Color(0xFFF9FFF6),
    onBackground = Color(0xFF003322),
    surfaceVariant = Color(0xFFEAF7EE)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF00E68A),
    onPrimary = Color.Black,
    secondary = Color(0xFFFFC54F),
    onSecondary = Color.Black,
    background = Color(0xFF001F14),
    onBackground = Color(0xFFE5FFE8),
    surfaceVariant = Color(0xFF00462C)
)

@Composable
fun InventoryManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
