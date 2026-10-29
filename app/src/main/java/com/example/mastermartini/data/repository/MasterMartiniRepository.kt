package com.example.mastermartini.data.repository

import com.example.mastermartini.data.model.BusinessSize
import com.example.mastermartini.data.model.BusinessType
import com.example.mastermartini.data.model.Lesson
import com.example.mastermartini.data.model.Product
import com.example.mastermartini.data.model.SampleRequest
import com.example.mastermartini.data.model.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Datos solo en memoria: nada de usuarios ni claves se guarda en el dispositivo.
 * Al cerrar la app se pierden los registros de la sesión. Singleton para sobrevivir a rotaciones.
 */
class MasterMartiniRepository internal constructor() {

    companion object {
        val shared: MasterMartiniRepository by lazy { MasterMartiniRepository() }
    }

    private var sessionEmail: String? = null

    // Cuentas de demostración (datos ficticios)
    private val accounts = mutableMapOf(
        "pasteleria@mastermartini.cl" to Pair(
            User(
                id = "usr_pasteleria_01",
                name = "Alejandro Morales",
                email = "pasteleria@mastermartini.cl",
                phone = "+56 9 8765 4321",
                businessName = "Pastelería y Café La Fontana",
                businessRut = "76.452.190-K",
                address = "Av. Providencia 1420, Santiago",
                businessType = BusinessType.PANADERIA_PASTELERIA,
                businessSize = BusinessSize.MEDIANO,
                interests = listOf("Cremas Vegetales y Decoración", "Masas de Hoja y Bollería", "Chocolatería"),
                favoriteLessonIds = setOf("les_02", "les_06"),
                completedLessonIds = setOf("les_02"),
                largeTextEnabled = false
            ),
            "123456"
        ),
        "chocolateria@mastermartini.cl" to Pair(
            User(
                id = "usr_choco_02",
                name = "Valentina Silva",
                email = "chocolateria@mastermartini.cl",
                phone = "+56 9 9123 4567",
                businessName = "Chocolates & Bombones Cacao Real",
                businessRut = "77.892.301-2",
                address = "Calle Italia 850, Ñuñoa",
                businessType = BusinessType.CHOCOLATERIA,
                businessSize = BusinessSize.ARTESANAL,
                interests = listOf("Chocolatería y Bombonería", "Rellenos y Coberturas"),
                favoriteLessonIds = setOf("les_01"),
                completedLessonIds = emptySet(),
                largeTextEnabled = false
            ),
            "123456"
        ),
        "panaderia@mastermartini.cl" to Pair(
            User(
                id = "usr_pan_03",
                name = "Don Pedro Alarcón",
                email = "panaderia@mastermartini.cl",
                phone = "+56 9 7654 3210",
                businessName = "Panadería San Jorge",
                businessRut = "78.123.456-7",
                address = "Gran Avenida 5200, San Miguel",
                businessType = BusinessType.PANADERIA_PASTELERIA,
                businessSize = BusinessSize.MEDIANO,
                interests = listOf("Masas de Hoja y Bollería", "Rellenos y Pastas"),
                favoriteLessonIds = setOf("les_03"),
                completedLessonIds = emptySet(),
                largeTextEnabled = true // Activo para usuario mayor de 50 años
            ),
            "123456"
        )
    )

    private val sampleRequestsList = mutableListOf(
        SampleRequest(
            id = "smp_01",
            userId = "usr_pasteleria_01",
            userEmail = "pasteleria@mastermartini.cl",
            productId = "prod_decor_up",
            productName = "Decor Up Crema Vegetal para Batir 1L",
            businessName = "Pastelería y Café La Fontana",
            contactPhone = "+56 9 8765 4321",
            deliveryAddress = "Av. Providencia 1420, Santiago",
            requestedAt = "15/10/2026",
            status = "Despachada",
            notes = "Muestra para prueba en tortas de matrimonio"
        )
    )

    fun login(email: String, pass: String): Result<User> {
        val key = email.trim().lowercase(Locale.ROOT)
        val account = accounts[key]
        // Mismo mensaje para correo inexistente y clave errónea: no revela qué cuentas existen
        if (account == null || account.second != pass) {
            return Result.failure(Exception("Correo o contraseña incorrectos. Revisa tus datos e inténtalo de nuevo."))
        }
        sessionEmail = key
        return Result.success(account.first)
    }

    fun register(user: User, pass: String): Result<User> {
        val key = user.email.trim().lowercase(Locale.ROOT)
        if (accounts.containsKey(key)) {
            return Result.failure(Exception("Ya existe una cuenta con ese correo. Inicia sesión."))
        }
        accounts[key] = Pair(user, pass)
        sessionEmail = key
        return Result.success(user)
    }

    /** Correos de las cuentas que existen ahora; sirve para borrar fotos de usuarios que ya no están. */
    fun knownEmails(): Set<String> = accounts.keys.toSet()

    fun getCurrentUser(): User? = sessionEmail?.let { accounts[it]?.first }

    fun updateUser(user: User) {
        val key = user.email.trim().lowercase(Locale.ROOT)
        accounts[key]?.let { accounts[key] = Pair(user, it.second) }
    }

    fun toggleFavorite(lessonId: String): User? {
        val current = getCurrentUser() ?: return null
        val newFavs = current.favoriteLessonIds.toMutableSet()
        if (newFavs.contains(lessonId)) {
            newFavs.remove(lessonId)
        } else {
            newFavs.add(lessonId)
        }
        val updated = current.copy(favoriteLessonIds = newFavs)
        updateUser(updated)
        return updated
    }

    fun toggleCompleted(lessonId: String): User? {
        val current = getCurrentUser() ?: return null
        val newCompleted = current.completedLessonIds.toMutableSet()
        if (newCompleted.contains(lessonId)) {
            newCompleted.remove(lessonId)
        } else {
            newCompleted.add(lessonId)
        }
        val updated = current.copy(completedLessonIds = newCompleted)
        updateUser(updated)
        return updated
    }

    fun toggleLargeText(): Boolean {
        val current = getCurrentUser() ?: return false
        val newSetting = !current.largeTextEnabled
        val updated = current.copy(largeTextEnabled = newSetting)
        updateUser(updated)
        return newSetting
    }

    fun createSampleRequest(
        product: Product,
        notes: String = ""
    ): SampleRequest {
        val user = getCurrentUser()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val request = SampleRequest(
            id = "smp_" + UUID.randomUUID().toString().take(8),
            userId = user?.id ?: "anon",
            userEmail = user?.email ?: "contacto@mastermartini.cl",
            productId = product.id,
            productName = product.name,
            businessName = user?.businessName ?: "Mi Negocio",
            contactPhone = user?.phone ?: "+56 9 0000 0000",
            deliveryAddress = user?.address ?: "Dirección por coordinar",
            requestedAt = dateFormat.format(Date()),
            status = "En preparación",
            notes = notes
        )
        sampleRequestsList.add(0, request)
        return request
    }

    fun getSampleRequests(): List<SampleRequest> {
        val user = getCurrentUser()
        return if (user != null) {
            sampleRequestsList.filter { it.userId == user.id || it.userEmail == user.email }
        } else {
            sampleRequestsList
        }
    }

    fun getAllLessons(): List<Lesson> = MasterMartiniData.LESSONS

    fun getRecommendedLessons(businessType: BusinessType): List<Lesson> {
        val direct = MasterMartiniData.LESSONS.filter { it.recommendedFor.contains(businessType) }
        return if (direct.isNotEmpty()) direct else MasterMartiniData.LESSONS
    }

    fun getLessonById(id: String): Lesson? =
        MasterMartiniData.LESSONS.firstOrNull { it.id == id }

    fun getAllProducts(): List<Product> = MasterMartiniData.PRODUCTS

    fun getProductById(id: String): Product? =
        MasterMartiniData.PRODUCTS.firstOrNull { it.id == id }

    fun logout() {
        sessionEmail = null
    }
}
