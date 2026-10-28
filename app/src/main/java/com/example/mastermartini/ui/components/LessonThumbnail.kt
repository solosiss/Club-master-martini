package com.example.mastermartini.ui.components

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mastermartini.data.model.Lesson

/** Miniatura oficial de YouTube; sin red o con error queda el fondo verde con ícono de play. */
@Composable
fun LessonThumbnail(lesson: Lesson, modifier: Modifier = Modifier, highRes: Boolean = false) {
    var loaded by remember(lesson.id) { mutableStateOf(false) }
    // maxres no existe para todos los videos: si falla se cae a la miniatura estándar
    var url by remember(lesson.id) { mutableStateOf(if (highRes) lesson.thumbnailUrl.replace("hqdefault", "maxresdefault") else lesson.thumbnailUrl) }
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        if (!loaded) {
            Icon(
                Icons.Default.PlayCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(48.dp)
            )
        }
        AsyncImage(
            model = url,
            contentDescription = lesson.title,
            contentScale = ContentScale.Crop,
            onSuccess = { loaded = true },
            onError = { if (url != lesson.thumbnailUrl) url = lesson.thumbnailUrl else loaded = false },
            modifier = Modifier.fillMaxSize()
        )
    }
}
