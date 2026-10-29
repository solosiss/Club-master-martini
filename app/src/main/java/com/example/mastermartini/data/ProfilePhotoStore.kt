package com.example.mastermartini.data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.security.MessageDigest

/** Nombre de archivo estable por usuario: no contiene el correo ni caracteres problemáticos. */
fun photoFileName(email: String): String {
    val digest = MessageDigest.getInstance("SHA-256").digest(email.trim().lowercase().toByteArray())
    return digest.take(8).joinToString("") { "%02x".format(it) } + ".jpg"
}

/**
 * Foto de perfil en el almacenamiento privado de la app (files/profile), excluida de las copias de seguridad.
 * Un archivo por usuario; "Quitar foto" lo borra de verdad.
 */
class ProfilePhotoStore(private val context: Context) {
    private val dir = File(context.filesDir, "profile").apply { mkdirs() }
    private val cameraDir = File(context.cacheDir, "camera").apply { mkdirs() }
    private val cameraFile = File(cameraDir, "capture.jpg")

    fun file(email: String): File = File(dir, photoFileName(email))

    fun has(email: String): Boolean = file(email).let { it.exists() && it.length() > 0 }

    /** Copia la imagen elegida o capturada; escribe en un temporal y renombra, para no dejar archivos a medias. */
    fun saveFrom(email: String, source: Uri): Boolean = runCatching {
        val target = file(email)
        val tmp = File(dir, target.name + ".tmp")
        context.contentResolver.openInputStream(source)!!.use { input -> tmp.outputStream().use { input.copyTo(it) } }
        if (tmp.length() == 0L) { tmp.delete(); return false }
        tmp.renameTo(target)
    }.getOrDefault(false)

    fun delete(email: String) { file(email).delete() }

    /** Borra las fotos de usuarios que ya no existen (por ejemplo, registrados en una sesión anterior). */
    fun cleanOrphans(knownEmails: Set<String>) {
        val keep = knownEmails.map { photoFileName(it) }.toSet()
        dir.listFiles()?.filter { it.name !in keep }?.forEach { it.delete() }
    }

    /** Uri para que la cámara del sistema escriba la foto sin necesitar permiso de cámara ni de almacenamiento. */
    fun cameraUri(): Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", cameraFile)

    fun clearCameraTemp() { cameraFile.delete() }
}
