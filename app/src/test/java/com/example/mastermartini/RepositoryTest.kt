package com.example.mastermartini

import com.example.mastermartini.data.model.BusinessSize
import com.example.mastermartini.data.model.BusinessType
import com.example.mastermartini.data.model.User
import com.example.mastermartini.data.repository.MasterMartiniRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RepositoryTest {
    private fun repo() = MasterMartiniRepository()

    private fun newUser(email: String) = User(
        id = "usr_test", name = "Test", email = email, phone = "", businessName = "Negocio de prueba",
        businessRut = "", address = "", businessType = BusinessType.RETAIL, businessSize = BusinessSize.ARTESANAL
    )

    @Test
    fun login_demoAccountWithCorrectPassword_succeeds() {
        val r = repo()
        assertTrue(r.login("pasteleria@mastermartini.cl", "123456").isSuccess)
        assertEquals("pasteleria@mastermartini.cl", r.getCurrentUser()?.email)
    }

    @Test
    fun login_wrongPassword_failsWithoutSession() {
        val r = repo()
        assertTrue(r.login("pasteleria@mastermartini.cl", "otra-clave").isFailure)
        assertNull(r.getCurrentUser())
    }

    @Test
    fun login_unknownEmail_doesNotAutoRegister() {
        val r = repo()
        assertTrue(r.login("nuevo@correo.cl", "123456").isFailure)
        assertNull(r.getCurrentUser())
    }

    @Test
    fun login_sameMessageForUnknownEmailAndWrongPassword() {
        val r = repo()
        val a = r.login("nuevo@correo.cl", "123456").exceptionOrNull()?.message
        val b = r.login("pasteleria@mastermartini.cl", "mala").exceptionOrNull()?.message
        assertEquals(a, b)
    }

    @Test
    fun register_thenLogin_worksAndDuplicateIsRejected() {
        val r = repo()
        assertTrue(r.register(newUser("Ana@Correo.cl"), "secreta1").isSuccess)
        r.logout()
        assertTrue(r.login("ana@correo.cl", "secreta1").isSuccess)
        assertTrue(r.register(newUser("ana@correo.cl"), "otra123").isFailure)
    }

    @Test
    fun logout_clearsSession() {
        val r = repo()
        r.login("pasteleria@mastermartini.cl", "123456")
        r.logout()
        assertNull(r.getCurrentUser())
    }

    @Test
    fun newInstance_forgetsRegisteredUsers() {
        repo().register(newUser("temp@correo.cl"), "secreta1")
        assertTrue(repo().login("temp@correo.cl", "secreta1").isFailure)
    }
}
