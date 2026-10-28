package com.example.mastermartini.data.model

import androidx.annotation.DrawableRes
import com.example.mastermartini.R

/**
 * Tipos de negocio atendidos por Master Martini Chile
 */
enum class BusinessType(val displayName: String, @DrawableRes val iconRes: Int, val description: String) {
    PANADERIA_PASTELERIA("Panadería y Pastelería", R.drawable.ic_biz_bread, "Pastelería de vitrina, masas de hoja, bollería, tortas y postres"),
    CHOCOLATERIA("Chocolatería", R.drawable.ic_biz_chocolate, "Moldeo, templado, bombones y coberturas"),
    GELATERIA("Gelatería", R.drawable.ic_biz_icecream, "Helado artesanal, bases en polvo y veteados"),
    FOOD_SERVICE("Food Service", R.drawable.ic_biz_chefhat, "Hoteles, casinos, cafés y restaurantes"),
    RETAIL("Retail", R.drawable.ic_biz_store, "Productos para el consumidor final");

    companion object {
        fun fromDisplayName(name: String): BusinessType {
            return entries.firstOrNull { it.displayName.equals(name, ignoreCase = true) } ?: PANADERIA_PASTELERIA
        }
    }
}

/**
 * Tamaño de la empresa o emprendimiento
 */
enum class BusinessSize(val displayName: String, val range: String) {
    ARTESANAL("Emprendimiento / Artesanal", "1 - 5 personas"),
    MEDIANO("Taller Mediano / Local", "6 - 20 personas"),
    INDUSTRIAL("Industrial / Gran Escala", "Más de 20 personas")
}

/**
 * Modelo de Usuario / Cliente
 */
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val businessName: String,
    val businessRut: String,
    val address: String,
    val businessType: BusinessType,
    val businessSize: BusinessSize,
    val interests: List<String> = emptyList(),
    val favoriteLessonIds: Set<String> = emptySet(),
    val completedLessonIds: Set<String> = emptySet(),
    val largeTextEnabled: Boolean = false
)

/**
 * Insumo oficial Master Martini
 */
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val line: String, // ej. Caravella, FiorFiore, Master Melange, Decor Up
    val description: String,
    val format: String,
    val sku: String,
    val badge: String = "Línea profesional",
    val technicalSheetUrl: String = "https://www.unigra.it/es/prodotti"
)

/**
 * Lección técnica / Video de capacitación
 */
data class Lesson(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val chefName: String,
    val durationSeconds: Int,
    val durationFormatted: String,
    val youtubeVideoId: String = "",
    val difficulty: String, // Básico, Intermedio, Avanzado
    val recommendedFor: List<BusinessType>,
    val technicalTips: List<String>,
    val productsUsed: List<Product>
) {
    val thumbnailUrl: String get() = "https://i.ytimg.com/vi/$youtubeVideoId/hqdefault.jpg"
}

/**
 * Solicitud de muestra técnica de insumos (comercial)
 */
data class SampleRequest(
    val id: String,
    val userId: String,
    val userEmail: String,
    val productId: String,
    val productName: String,
    val businessName: String,
    val contactPhone: String,
    val deliveryAddress: String,
    val requestedAt: String,
    val status: String = "En preparación", // Recibida, En preparación, Despachada
    val notes: String = ""
)
