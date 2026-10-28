package com.example.mastermartini.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mastermartini.ui.theme.MartiniGreen

/** Franja verde fija detrás de la barra de estado: los íconos blancos siguen legibles al desplazar la lista. */
@Composable
fun StatusBarScrim(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsTopHeight(WindowInsets.statusBars)
            .background(MartiniGreen)
    )
}
