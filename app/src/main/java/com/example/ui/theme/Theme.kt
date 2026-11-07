package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = UnionGreenDarkPrimary,
    onPrimary = Color(0xFF00390E),
    primaryContainer = Color(0xFF005318),
    onPrimaryContainer = Color(0xFFA6F5A3),
    secondary = UnionGreenDarkSecondary,
    onSecondary = Color(0xFF053815),
    tertiary = UnionGreenDarkTertiary,
    background = UnionSurfaceDark,
    surface = UnionCardBgDark,
    onBackground = Color(0xFFE2E3DE),
    onSurface = Color(0xFFE2E3DE),
    error = Color(0xFFFFB4AB),
    outline = UnionOutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = UnionGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8F5E9),
    onPrimaryContainer = Color(0xFF002106),
    secondary = UnionGreenSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDCEDC8),
    onSecondaryContainer = Color(0xFF1B370D),
    tertiary = UnionGreenTertiary,
    onTertiary = Color.White,
    background = UnionSurfaceLight,
    surface = UnionCardBgLight,
    onBackground = Color(0xFF1A1C19),
    onSurface = Color(0xFF1A1C19),
    error = UnionEmergencyRed,
    errorContainer = UnionEmergencyRedLight,
    onError = Color.White,
    outline = UnionOutlineLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep distinct custom emerald branding
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
