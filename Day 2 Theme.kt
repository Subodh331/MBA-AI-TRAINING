package com.marketiq.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val CorporateIndigo = Color(0xFF4338CA)
val CorporateIndigoDark = Color(0xFF3730A3)
val IndigoContainer = Color(0xFFE0E7FF)
val IndigoOnContainer = Color(0xFF1E1B4B)

val SlateCanvas = Color(0xFFF8FAFC)
val SlateSurface = Color(0xFFFFFFFF)
val SlateSurfaceVariant = Color(0xFFF1F5F9)
val SlateOutline = Color(0xFFE2E8F0)
val SlateTextPrimary = Color(0xFF0F172A)
val SlateTextSecondary = Color(0xFF475569)
val EmeraldPositive = Color(0xFF15803D)

private val MarketiQLightColors = lightColorScheme(
    primary = CorporateIndigo,
    onPrimary = Color.White,
    primaryContainer = IndigoContainer,
    onPrimaryContainer = IndigoOnContainer,
    secondary = SlateTextSecondary,
    onSecondary = Color.White,
    secondaryContainer = SlateSurfaceVariant,
    onSecondaryContainer = SlateTextPrimary,
    background = SlateCanvas,
    onBackground = SlateTextPrimary,
    surface = SlateSurface,
    onSurface = SlateTextPrimary,
    surfaceVariant = SlateSurfaceVariant,
    onSurfaceVariant = SlateTextSecondary,
    outline = SlateOutline
)

private val MarketiQTypography = Typography(
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        color = SlateTextPrimary
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        color = SlateTextPrimary
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = SlateTextPrimary
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = SlateTextSecondary
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)

@Composable
fun MarketiQTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MarketiQLightColors,
        typography = MarketiQTypography,
        content = content
    )
}
