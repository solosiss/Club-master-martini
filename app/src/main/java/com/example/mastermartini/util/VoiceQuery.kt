package com.example.mastermartini.util

/** Limpia el texto dictado: espacios repetidos y puntuación en los bordes ("crema para batir."). */
fun cleanVoiceQuery(raw: String?): String =
    raw.orEmpty().replace(Regex("\\s+"), " ").trim { it in ".,;:!?¿¡ " }
