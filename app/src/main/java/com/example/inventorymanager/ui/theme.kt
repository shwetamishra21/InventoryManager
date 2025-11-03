package com.example.inventorymanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = GreenPrimary,
    onPrimary = GreenOnPrimary,
    secondary = GreenSecondary,
    onSecondary = GreenOnPrimary,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = CardDark,
    onSurface = GreenOnPrimary,
    error = ErrorRed
)

@Composable
fun InventoryManagerTheme(
    useDarkTheme: Boolean = true, // Force dark theme for better appearance
    content: @Composable () -> Unit
) {
    val colors = DarkColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
