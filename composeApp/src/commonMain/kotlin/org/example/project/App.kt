package org.example.project


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import dreemhunter.composeapp.generated.resources.Res
import dreemhunter.composeapp.generated.resources.arial
import dreemhunter.composeapp.generated.resources.arialbd
import dreemhunter.composeapp.generated.resources.arialbi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val displayFontFamily = FontFamily(
        listOf(
            Font(
                resource = Res.font.arialbd,
                weight = FontWeight.Bold ,
                style = FontStyle.Normal,
                variationSettings =  FontVariation.Settings()
            ),
            Font(
                resource = Res.font.arial,
                weight = FontWeight.Normal ,
                style = FontStyle.Normal,
                variationSettings =  FontVariation.Settings()
            ),
            Font(
                resource = Res.font.arialbi,
                weight = FontWeight.Bold ,
                style = FontStyle.Italic,
                variationSettings =  FontVariation.Settings()
            )
        )
    )
    val baseline = Typography()
    val AppTypography = Typography(

        displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = displayFontFamily),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = displayFontFamily),
        bodySmall = baseline.bodySmall.copy(fontFamily = displayFontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = displayFontFamily),
        labelMedium = baseline.labelMedium.copy(fontFamily = displayFontFamily),
        labelSmall = baseline.labelSmall.copy(fontFamily = displayFontFamily)
    )
   // val darkTheme: Boolean = isSystemInDarkTheme()
//    MaterialTheme(
//        colorScheme = if (darkTheme) darkScheme else lightScheme
    MaterialTheme(
       // colorScheme =  if (darkTheme) darkScheme else lightScheme,
        typography = AppTypography,
        content = content,
    )
}

@Composable
fun App() {
    AppTheme {
        GameScreen()
    }
}