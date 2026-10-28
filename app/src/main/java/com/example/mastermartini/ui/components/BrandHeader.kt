package com.example.mastermartini.ui.components

import com.example.mastermartini.ui.theme.MartiniGreenPale
import androidx.compose.ui.Alignment
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.annotation.DrawableRes
import com.example.mastermartini.R
import androidx.compose.ui.unit.dp

/** Franja verde de marca; con onBack muestra el botón de volver arriba a la izquierda (56 dp). */
@Composable
fun BrandHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .padding(end = 16.dp, bottom = 16.dp, start = if (onBack == null) 16.dp else 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            IconButton(onClick = onBack, modifier = Modifier.size(56.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
            }
        }
        Column(modifier = Modifier.weight(1f).padding(top = if (onBack == null) 16.dp else 0.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge, color = Color.White)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.bodyLarge, color = MartiniGreenPale)
            }
        }
    }
}

/** Ícono por categoría de insumo (mismo set Tabler que los rubros). */
@DrawableRes
fun categoryIcon(category: String): Int = when (category) {
    "Chocolatería" -> R.drawable.ic_biz_chocolate
    "Heladería" -> R.drawable.ic_biz_icecream
    "Masas y Bollería" -> R.drawable.ic_biz_bread
    "Cremas Vegetales", "Rellenos y Pastas" -> R.drawable.ic_biz_cake
    else -> R.drawable.ic_biz_chefhat
}
