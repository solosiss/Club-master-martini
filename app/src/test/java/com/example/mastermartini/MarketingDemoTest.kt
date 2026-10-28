package com.example.mastermartini

import com.example.mastermartini.data.model.BusinessType
import com.example.mastermartini.data.repository.MarketingDemo
import com.example.mastermartini.data.repository.MasterMartiniData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MarketingDemoTest {
    @Test
    fun leads_coverEveryBusinessType_sortedDescending() {
        val leads = MarketingDemo.leadsByRubro
        assertEquals(BusinessType.entries.toSet(), leads.map { it.first }.toSet())
        assertEquals(leads.map { it.second }.sortedDescending(), leads.map { it.second })
    }

    @Test
    fun totals_matchTheirParts() {
        assertEquals(MarketingDemo.leadsByRubro.sumOf { it.second }, MarketingDemo.totalLeads)
        assertEquals(MarketingDemo.samplesByStatus.sumOf { it.second }, MarketingDemo.totalSamples)
    }

    @Test
    fun topViewed_pointToRealLessons_sortedDescending() {
        val ids = MasterMartiniData.LESSONS.map { it.id }.toSet()
        assertTrue(MarketingDemo.topViewed.all { it.first in ids })
        assertEquals(MarketingDemo.topViewed.map { it.second }.sortedDescending(), MarketingDemo.topViewed.map { it.second })
    }
}
