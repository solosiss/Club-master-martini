package com.example.mastermartini.util

import android.content.Context
import android.content.Intent
import com.example.mastermartini.data.model.Lesson

/** Mensaje de la receta para WhatsApp, correo, etc. Los tips son datos de ejemplo hasta que Master Martini los valide. */
fun buildShareText(lesson: Lesson, includeTips: Boolean = true): String = buildString {
    appendLine(lesson.title)
    val chef = lesson.chefName.substringBefore("(").trim()
    if (chef.isNotBlank()) appendLine("Por $chef")
    if (lesson.productsUsed.isNotEmpty()) {
        appendLine()
        appendLine("Insumos Master Martini:")
        lesson.productsUsed.forEach { appendLine("- ${it.name}") }
    }
    if (includeTips && lesson.technicalTips.isNotEmpty()) {
        appendLine()
        appendLine("Tips:")
        lesson.technicalTips.forEach { appendLine("- $it") }
    }
    appendLine()
    appendLine("Mira el video: https://youtu.be/${lesson.youtubeVideoId}")
    appendLine()
    append("Club Master Martini")
}

/** Abre el selector de Android (WhatsApp, correo, Mensajes...). */
fun shareLesson(context: Context, lesson: Lesson) {
    val send = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Receta: ${lesson.title}")
        putExtra(Intent.EXTRA_TEXT, buildShareText(lesson))
    }
    context.startActivity(Intent.createChooser(send, "Compartir receta"))
}
