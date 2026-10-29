package com.example.mastermartini

import com.example.mastermartini.data.photoFileName
import com.example.mastermartini.data.repository.MasterMartiniData
import com.example.mastermartini.util.buildShareText
import com.example.mastermartini.util.cleanVoiceQuery
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NativeHelpersTest {
    private val withProducts = MasterMartiniData.LESSONS.first { it.productsUsed.isNotEmpty() }
    private val withoutProducts = MasterMartiniData.LESSONS.first { it.productsUsed.isEmpty() }

    @Test
    fun share_includesTitleChefProductsTipsAndRealYoutubeLink() {
        val t = buildShareText(withProducts)
        assertTrue(t.contains(withProducts.title))
        assertTrue(t.contains(withProducts.chefName.substringBefore("(").trim()))
        withProducts.productsUsed.forEach { assertTrue(t.contains(it.name)) }
        withProducts.technicalTips.forEach { assertTrue(t.contains(it)) }
        assertTrue(t.contains("https://youtu.be/${withProducts.youtubeVideoId}"))
    }

    @Test
    fun share_neverUsesTheInventedWebLink() {
        assertFalse(buildShareText(withProducts).contains("mastermartini.cl/cursos"))
    }

    @Test
    fun share_withoutTips_omitsThemButKeepsLink() {
        val t = buildShareText(withProducts, includeTips = false)
        withProducts.technicalTips.forEach { assertFalse(t.contains(it)) }
        assertTrue(t.contains("https://youtu.be/"))
    }

    @Test
    fun share_lessonWithoutProducts_hasNoProductsSection() {
        assertFalse(buildShareText(withoutProducts).contains("Insumos"))
    }

    @Test
    fun voice_trimsSpacesAndTrailingPunctuation() {
        assertEquals("crema para batir", cleanVoiceQuery("  crema   para batir.  "))
        assertEquals("chocolate", cleanVoiceQuery("chocolate,"))
    }

    @Test
    fun voice_nullOrBlank_givesEmpty() {
        assertEquals("", cleanVoiceQuery(null))
        assertEquals("", cleanVoiceQuery("   "))
    }

    @Test
    fun photoFileName_isStableAcrossCaseAndSpaces() {
        assertEquals(photoFileName("Ana@Correo.cl"), photoFileName("  ana@correo.cl "))
    }

    @Test
    fun photoFileName_differsPerUser_andIsSafeAsAFileName() {
        val a = photoFileName("ana@correo.cl")
        assertNotEquals(a, photoFileName("luis@correo.cl"))
        assertTrue(a.endsWith(".jpg"))
        assertFalse(a.contains("@") || a.contains("/") || a.contains(" "))
    }
}
