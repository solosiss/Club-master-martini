package com.example.mastermartini.data.repository

import com.example.mastermartini.data.model.BusinessType

/** Cifras ficticias solo para la demostración comercial; la pantalla las rotula como tales. */
object MarketingDemo {
    val leadsByRubro: List<Pair<BusinessType, Int>> = listOf(
        BusinessType.PANADERIA_PASTELERIA to 412,
        BusinessType.CHOCOLATERIA to 268,
        BusinessType.FOOD_SERVICE to 203,
        BusinessType.GELATERIA to 187,
        BusinessType.RETAIL to 114
    )

    /** Orden del flujo: recibida, en preparación, despachada. */
    val samplesByStatus: List<Pair<String, Int>> = listOf(
        "Recibidas" to 64,
        "En preparación" to 97,
        "Despachadas" to 189
    )

    /** Pares (id de lección, visualizaciones). */
    val topViewed: List<Pair<String, Int>> = listOf(
        "les_04" to 842,
        "les_02" to 716,
        "les_06" to 655
    )

    val totalLeads: Int get() = leadsByRubro.sumOf { it.second }
    val totalSamples: Int get() = samplesByStatus.sumOf { it.second }
}
