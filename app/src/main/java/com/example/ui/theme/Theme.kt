package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    background = TrueBlack,
    surface = GlassContainer,
    surfaceVariant = GlassContainerLight,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color.White,
    primary = Color.White,
    onPrimary = TrueBlack,
    outline = GlassBorder,
    outlineVariant = GlassBorder,
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force dark theme for OLED signature identity
    dynamicColor: Boolean = false, // Disable dynamic color to enforce branding
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
