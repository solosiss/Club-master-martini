package com.example.mastermartini.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = MartiniGreen,
    onPrimary = BakerySurface,
    primaryContainer = MartiniGreenMid,
    onPrimaryContainer = BakerySurface,
    secondary = MartiniGold,
    onSecondary = TextPrimary,
    secondaryContainer = MartiniGoldLight,
    onSecondaryContainer = MartiniGreenDark,
    tertiary = MartiniGreenMid,
    onTertiary = BakerySurface,
    background = BakeryWarmBackground,
    onBackground = TextPrimary,
    surface = BakerySurface,
    onSurface = TextPrimary,
    surfaceVariant = BakerySurfaceVariant,
    onSurfaceVariant = TextSecondary,
    error = ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = MartiniGold,
    onPrimary = MartiniGreenDark,
    primaryContainer = MartiniGreenMid,
    onPrimaryContainer = BakerySurface,
    secondary = MartiniGoldLight,
    onSecondary = TextPrimary,
    background = MartiniGreenDark,
    surface = MartiniGreen,
    onBackground = BakerySurface,
    onSurface = BakerySurface
)

@Composable
fun MastermartiniTheme(
    darkTheme: Boolean = false, // las pantallas usan superficies claras fijas (uso en cocina, alto contraste)
    largeText: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.statusBarColor = colorScheme.primary.toArgb()
            val insetsController = window?.let { WindowCompat.getInsetsController(it, view) }
            insetsController?.isAppearanceLightStatusBars = false
        }
    }

    // Modo Lectura Aumentada: escala todo el texto (incluido el fijo en sp) 25%
    val density = LocalDensity.current
    val scaled = if (largeText) Density(density.density, density.fontScale * 1.25f) else density
    CompositionLocalProvider(LocalDensity provides scaled) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
