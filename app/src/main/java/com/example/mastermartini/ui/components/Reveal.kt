package com.example.mastermartini.ui.components

import android.provider.Settings
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/** False cuando el usuario desactivó las animaciones del sistema (reducir movimiento). */
@Composable
fun rememberAnimationsEnabled(): Boolean {
    val resolver = LocalContext.current.contentResolver
    return remember { Settings.Global.getFloat(resolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) != 0f }
}

/** Entrada escalonada: aparece subiendo 24 dp. [index] ordena la secuencia (90 ms por posición). */
@Composable
fun Reveal(index: Int, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val enabled = rememberAnimationsEnabled()
    val progress = remember { Animatable(if (enabled) 0f else 1f) }
    LaunchedEffect(Unit) {
        if (enabled) {
            delay(index * 90L)
            progress.animateTo(1f, tween(500, easing = FastOutSlowInEasing))
        }
    }
    Box(
        modifier = modifier.graphicsLayer {
            alpha = progress.value
            translationY = (1f - progress.value) * 24.dp.toPx()
        }
    ) { content() }
}
