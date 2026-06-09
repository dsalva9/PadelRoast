package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PadelColorScheme = lightColorScheme(
    primary = PadelLime,
    secondary = ElectricCyan,
    tertiary = ActiveCoral,
    background = SlateDark,
    surface = SlateSurface,
    surfaceVariant = SlateSurfaceVariant,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextLight,
    onSurface = TextLight,
    onSurfaceVariant = TextMuted
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false, // Force vibrant light theme for playful contrast
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PadelColorScheme,
        typography = Typography,
        content = content
    )
}
