package com.aadityaverma.solutionexplorer.ui.theme

import android.app.Activity
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

val Purple80 = Color(0xFF800080)
val PurpleGrey80 = Color(0xFF808080)
val Pink80 = Color(0xFFFFC0CB)

val Purple40 = Color(0xFFFFEB3B)
val PurpleGrey40 = Color(0xFF404040)
val Pink40 = Color(0xFFFFB6C1)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = newcolor3,
)

@Composable
fun SolutionExplorerTheme(
    darkTheme: Boolean = false, // Force dark theme based on your colors
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}