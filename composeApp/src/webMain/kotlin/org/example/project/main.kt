package org.example.project

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.ComposeViewport
import dreemhunter.composeapp.generated.resources.Res
import dreemhunter.composeapp.generated.resources.arial
import dreemhunter.composeapp.generated.resources.arialbd
import dreemhunter.composeapp.generated.resources.arialbi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        val arialFontFamily = FontFamily(listOf(
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
        MyFont.fontFamily = arialFontFamily
        App()
    }
}