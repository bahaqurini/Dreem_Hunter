package org.example.project


import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(fontFamily: FontFamily?=null) {
    GameScreen(fontFamily=fontFamily)
}