package com.example.mastermartini

import com.example.mastermartini.data.model.BusinessType
import com.example.mastermartini.data.repository.MasterMartiniData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ContentTest {
    private val lessons = MasterMartiniData.LESSONS

    @Test
    fun fromDisplayName_unknown_fallsBack() {
        assertEquals(BusinessType.PANADERIA_PASTELERIA, BusinessType.fromDisplayName("Pastelería"))
    }

    @Test
    fun lessons_haveValidYoutubeIds() {
        val id = Regex("^[A-Za-z0-9_-]{11}$")
        lessons.forEach {
            assertTrue(it.youtubeVideoId, id.matches(it.youtubeVideoId))
            assertFalse(it.youtubeVideoId == "dQw4w9WgXcQ")
        }
    }

    @Test
    fun lessons_haveUniqueIds() {
        assertEquals(lessons.size, lessons.map { it.id }.toSet().size)
        assertEquals(lessons.size, lessons.map { it.youtubeVideoId }.toSet().size)
    }

    @Test
    fun lessons_coverEveryBusinessType() {
        val covered = lessons.flatMap { it.recommendedFor }.toSet()
        assertEquals(BusinessType.entries.toSet(), covered)
    }

    @Test
    fun thumbnail_isDerivedFromId() {
        val l = lessons.first()
        assertEquals("https://i.ytimg.com/vi/${l.youtubeVideoId}/hqdefault.jpg", l.thumbnailUrl)
    }
}
