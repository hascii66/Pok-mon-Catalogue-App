package com.example.pokmoncatalogueapp.ui.theme

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
import com.example.pokmoncatalogueapp.constant.PokemonType

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val PokemonRed = Color(0xFFEF5350)
val PokemonBlue = Color(0xFF42A5F5)
val BackgroundWhite = Color(0xFFF5F5F5)

val AppColorScheme = lightColorScheme(
    primary = PokemonRed,
    secondary = PokemonBlue,
    background = BackgroundWhite
)

fun getTypeColor(type: String): Color {
    return when (type) {
        PokemonType.FIRE -> Color(0xFFF08030)
        PokemonType.WATER -> Color(0xFF6890F0)
        PokemonType.GRASS -> Color(0xFF78C850)
        PokemonType.ELECTRIC -> Color(0xFFF8D030)
        PokemonType.PSYCHIC -> Color(0xFFF85888)
        PokemonType.POISON -> Color(0xFFA040A0)
        PokemonType.BUG -> Color(0xFFA8B820)
        PokemonType.GROUND -> Color(0xFFE0C068)
        PokemonType.ROCK -> Color(0xFFB8A038)
        PokemonType.FIGHTING -> Color(0xFFC03028)
        else -> Color(0xFFA8A878)
    }
}

@Composable
fun PokémonCatalogueAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
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