package com.example.mastermartini.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import android.app.Activity

/** Íconos oscuros en la barra de estado mientras la pantalla tiene fondo claro; al salir vuelve a íconos claros (cabeceras verdes). */
@Composable
fun DarkStatusBarIcons() {
    val view = LocalView.current
    DisposableEffect(view) {
        val window = (view.context as? Activity)?.window
        val controller = window?.let { WindowCompat.getInsetsController(it, view) }
        controller?.isAppearanceLightStatusBars = true
        onDispose { controller?.isAppearanceLightStatusBars = false }
    }
}
