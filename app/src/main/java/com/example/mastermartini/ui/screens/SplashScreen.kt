package com.example.mastermartini.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.example.mastermartini.ui.components.BrandLogo
import com.example.mastermartini.ui.components.DarkStatusBarIcons
import com.example.mastermartini.ui.theme.BakeryWarmBackground
import com.example.mastermartini.ui.theme.MartiniGreen
import kotlinx.coroutines.delay

// Mitad crema (el logo oficial solo se lee completo sobre claro) y mitad verde de marca, separadas por una curva
@Composable
fun SplashScreen(onFinished: () -> Unit) {
    DarkStatusBarIcons()
    LaunchedEffect(Unit) {
        delay(1400)
        onFinished()
    }
    Box(modifier = Modifier.fillMaxSize().background(BakeryWarmBackground)) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val wave = Path().apply {
                moveTo(0f, h * 0.586f)
                cubicTo(w * 0.333f, h * 0.70f, w * 0.667f, h * 0.43f, w, h * 0.53f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(wave, MartiniGreen)
        }
        Box(Modifier.fillMaxWidth().fillMaxHeight(0.58f), contentAlignment = Alignment.Center) {
            BrandLogo(width = 240.dp)
        }
    }
}
