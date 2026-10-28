package com.example.mastermartini.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.mastermartini.R

/** Logo oficial (PNG del sitio escalado 4x; reemplazar por SVG/PNG en alta cuando Master Martini lo entregue). */
@Composable
fun BrandLogo(width: Dp, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.logo_master_martini),
        contentDescription = "Master Martini",
        modifier = modifier.width(width)
    )
}
