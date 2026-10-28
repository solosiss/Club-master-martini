package com.example.mastermartini.data.repository

import com.example.mastermartini.data.model.BusinessType
import com.example.mastermartini.data.model.Lesson
import com.example.mastermartini.data.model.Product

/**
 * Videos y miniaturas: canal oficial de YouTube @mastermartinichile.
 * Descripciones, tips, dificultad, insumos y SKU son datos de ejemplo (el caso exige datos sintéticos).
 * Líneas Ariba, Decor Up, FiorFiore, Mirall y Máster vienen del informe: por validar con el socio formador.
 * Duración 0 = desconocida, la UI la oculta.
 */
object MasterMartiniData {

    val PRODUCTS = listOf(
        Product(
            id = "prod_caravella_dark",
            name = "Caravella Cover Dark Discos",
            category = "Chocolatería",
            line = "Caravella",
            description = "Sucedáneo semiamargo en discos para moldeo, bañado y decoración. Excelente brillo y quiebre sin necesidad de curva compleja de templado.",
            format = "Caja 10 kg (10 x 1 kg)",
            sku = "MM-CAR-01",
            badge = "Alta Fluidez"
        ),
        Product(
            id = "prod_caravella_white",
            name = "Caravella Cover White Discos",
            category = "Chocolatería",
            line = "Caravella",
            description = "Sucedáneo de chocolate blanco de alta pureza con aroma natural a vainilla. Especial para figuras huecas y ganaches estables.",
            format = "Caja 10 kg (10 x 1 kg)",
            sku = "MM-CAR-02",
            badge = "Brillo Óptimo"
        ),
        Product(
            id = "prod_ariba_fondente",
            name = "Ariba Fondente 60% Cacao Puro",
            category = "Chocolatería",
            line = "Ariba",
            description = "Chocolate real con manteca de cacao pura. Sabor balanceado con notas frutales, perfecto para bombonería fina de vitrina.",
            format = "Caja 10 kg (gotas)",
            sku = "MM-ARI-60",
            badge = "Chocolate real"
        ),
        Product(
            id = "prod_decor_up",
            name = "Decor Up Crema Vegetal para Batir",
            category = "Cremas Vegetales",
            line = "Decor Up",
            description = "Crema vegetal profesional. Rendimiento superior (+3.5 veces el volumen), resiste temperaturas cálidas en vitrina sin desuerar.",
            format = "Tetra Pak 1 Litro (caja 12 unidades)",
            sku = "MM-DEC-01",
            badge = "Estabilidad Térmica"
        ),
        Product(
            id = "prod_bravo_crem",
            name = "Bravo Crem Neutra Multiuso",
            category = "Cremas Vegetales",
            line = "Bravo Crem",
            description = "Crema vegetal sin azúcar añadida para preparaciones dulces y saladas en cocinas de alto rendimiento y pastelería.",
            format = "Tetra Pak 1 Litro (caja 12 unidades)",
            sku = "MM-BRV-01",
            badge = "Sin Azúcar Añadida"
        ),
        Product(
            id = "prod_master_melange",
            name = "Margarina Máster Melange Croissant",
            category = "Masas y Bollería",
            line = "Máster",
            description = "Margarina técnica con 15% de mantequilla concentrada. Elasticidad ideal para laminado continuo en hojaldres, medialunas y croissants.",
            format = "Bloque 2 kg (caja 5 x 2 kg)",
            sku = "MM-MEL-02",
            badge = "Elasticidad Superior"
        ),
        Product(
            id = "prod_master_hojaldre",
            name = "Máster Hojaldre Placas Laminadas",
            category = "Masas y Bollería",
            line = "Máster",
            description = "Grasa vegetal formulada en placas listas para empastar. Garantiza capas de milhojas definidas y crocantes aún en obradores calurosos.",
            format = "Placas de 2 kg (caja 10 kg)",
            sku = "MM-HOJ-05",
            badge = "Fácil Laminado"
        ),
        Product(
            id = "prod_fiorfiore_pastelera",
            name = "FiorFiore Crema Pastelera Horno-Resistente",
            category = "Rellenos y Pastas",
            line = "FiorFiore",
            description = "Relleno listo para usar, estable a temperaturas de horneo (hasta 220°C). No hierve ni revienta las masas de facturas y berlines.",
            format = "Manga de 1 kg o Balde 6 kg",
            sku = "MM-FIO-09",
            badge = "Termoestable 220°C"
        ),
        Product(
            id = "prod_mirall_glaze",
            name = "Mirall Gel Espejo Chocolate",
            category = "Rellenos y Pastas",
            line = "Mirall",
            description = "Glaseado brillante listo para aplicar a 32°C. Cobertura lisa, reflejo cristalino que no se opaca al congelar ni en frío positivo.",
            format = "Balde 3 kg",
            sku = "MM-MIR-01",
            badge = "Brillo Espejo"
        ),
        Product(
            id = "prod_base_gelato",
            name = "Base FiorFiore Gelato 50",
            category = "Heladería",
            line = "Martini Gelato",
            description = "Base en polvo completa con emulsionantes y estabilizantes para estructura sedosa y cálida al paladar.",
            format = "Bolsa 2 kg (caja 8 x 2 kg)",
            sku = "MM-GEL-50",
            badge = "Alta cremosidad"
        )
    )

    val LESSONS = listOf(
        Lesson(
            id = "les_01",
            title = "Pancake de Avena y Plátano con Chef Lili Cañete",
            description = "Receta simple de desayuno del programa Sabores, Zona Latina, preparada por la chef Lili Cañete.",
            category = "Recetas",
            chefName = "Chef Lili Cañete",
            durationSeconds = 0,
            durationFormatted = "",
            youtubeVideoId = "Dz20QKTeK5I",
            difficulty = "Básico",
            recommendedFor = listOf(BusinessType.PANADERIA_PASTELERIA, BusinessType.RETAIL),
            technicalTips = listOf(
                "Usa plátano maduro para endulzar la mezcla.",
                "Cocina a fuego medio para dorar sin quemar."
            ),
            productsUsed = listOf()
        ),
        Lesson(
            id = "les_02",
            title = "Tips y Usos del Chocolate con Chef Lili Cañete",
            description = "Consejos para trabajar y aprovechar el chocolate en preparaciones de pastelería.",
            category = "Chocolatería",
            chefName = "Chef Lili Cañete",
            durationSeconds = 0,
            durationFormatted = "",
            youtubeVideoId = "v_WinlHRQt0",
            difficulty = "Básico",
            recommendedFor = listOf(BusinessType.CHOCOLATERIA),
            technicalTips = listOf(
                "Funde el chocolate a baja temperatura y sin agua.",
                "Trabaja en un lugar fresco y seco."
            ),
            productsUsed = listOf(PRODUCTS[0], PRODUCTS[1])
        ),
        Lesson(
            id = "les_03",
            title = "¡Qué fácil es con Chocolate Caravella!",
            description = "Cómo usar el chocolate Caravella en preparaciones del día a día.",
            category = "Chocolatería",
            chefName = "Equipo Master Martini",
            durationSeconds = 0,
            durationFormatted = "",
            youtubeVideoId = "C1HaDB9US_Y",
            difficulty = "Básico",
            recommendedFor = listOf(BusinessType.CHOCOLATERIA, BusinessType.PANADERIA_PASTELERIA),
            technicalTips = listOf(
                "Funde en baño maría o microondas a potencia media.",
                "Mezcla bien antes de moldear."
            ),
            productsUsed = listOf(PRODUCTS[0])
        ),
        Lesson(
            id = "les_04",
            title = "Torta Chocolate Avellana con Caravella Deco",
            description = "Armado de una torta de chocolate y avellana con cobertura Caravella.",
            category = "Pastelería",
            chefName = "Equipo Master Martini",
            durationSeconds = 0,
            durationFormatted = "",
            youtubeVideoId = "ZwOkabO177M",
            difficulty = "Básico",
            recommendedFor = listOf(BusinessType.PANADERIA_PASTELERIA, BusinessType.CHOCOLATERIA),
            technicalTips = listOf(
                "Enfría el relleno antes de armar.",
                "Cubre con la temperatura indicada en el envase."
            ),
            productsUsed = listOf(PRODUCTS[0])
        ),
        Lesson(
            id = "les_05",
            title = "¿Cómo utilizar crema pastelera, merengue o crema chantilly en polvo? | Delice",
            description = "Uso de las preparaciones en polvo Delice para cremas y merengues.",
            category = "Rellenos y Cremas",
            chefName = "Equipo Master Martini",
            durationSeconds = 0,
            durationFormatted = "",
            youtubeVideoId = "1U1twaw6bP0",
            difficulty = "Básico",
            recommendedFor = listOf(BusinessType.PANADERIA_PASTELERIA),
            technicalTips = listOf(
                "Respeta la proporción de agua o leche del envase.",
                "Bate el tiempo recomendado para lograr la textura."
            ),
            productsUsed = listOf(PRODUCTS[7])
        ),
        Lesson(
            id = "les_06",
            title = "¿Cómo utilizar la crema Vegetal? | Bravo Crem Azucarada",
            description = "Guía de uso de la crema vegetal Bravo Crem azucarada para batir.",
            category = "Cremas Vegetales",
            chefName = "Equipo Master Martini",
            durationSeconds = 0,
            durationFormatted = "",
            youtubeVideoId = "3n-h0n7Ttks",
            difficulty = "Básico",
            recommendedFor = listOf(BusinessType.PANADERIA_PASTELERIA, BusinessType.FOOD_SERVICE),
            technicalTips = listOf(
                "Mantén la crema bien fría antes de batir.",
                "Bate de menos a más velocidad."
            ),
            productsUsed = listOf(PRODUCTS[4])
        ),
        Lesson(
            id = "les_07",
            title = "Helado Artesanal Frutos del Bosque | Gelato Martini",
            description = "Preparación de helado artesanal de frutos del bosque con base Martini Gelato.",
            category = "Heladería",
            chefName = "Equipo Master Martini",
            durationSeconds = 0,
            durationFormatted = "",
            youtubeVideoId = "R2N_oOG0xI8",
            difficulty = "Básico",
            recommendedFor = listOf(BusinessType.GELATERIA),
            technicalTips = listOf(
                "Pesa la base con precisión.",
                "Mantén la mezcla en frío antes de mantecar."
            ),
            productsUsed = listOf(PRODUCTS[9])
        ),
        Lesson(
            id = "les_08",
            title = "Helado Artesanal de Avellana | Base Blanca Martini Gelato",
            description = "Receta de helado de avellana a partir de la base blanca Martini Gelato.",
            category = "Heladería",
            chefName = "Equipo Master Martini",
            durationSeconds = 0,
            durationFormatted = "",
            youtubeVideoId = "KhhA9pAJZxA",
            difficulty = "Básico",
            recommendedFor = listOf(BusinessType.GELATERIA),
            technicalTips = listOf(
                "Incorpora la pasta de avellana de forma gradual.",
                "Sirve a la temperatura de vitrina recomendada."
            ),
            productsUsed = listOf(PRODUCTS[9])
        ),
        Lesson(
            id = "les_09",
            title = "Lomo Strogonoff | Bravo Crem Cocina",
            description = "Plato de fondo con crema vegetal Bravo Crem Cocina, pensado para servicio de alto volumen.",
            category = "Cocina",
            chefName = "Equipo Master Martini",
            durationSeconds = 0,
            durationFormatted = "",
            youtubeVideoId = "Qwv-Uxbivqw",
            difficulty = "Básico",
            recommendedFor = listOf(BusinessType.FOOD_SERVICE),
            technicalTips = listOf(
                "Agrega la crema al final para que no se corte.",
                "Ajusta la sal antes de servir."
            ),
            productsUsed = listOf(PRODUCTS[4])
        ),
        Lesson(
            id = "les_10",
            title = "Panna Cotta | Orasì Barista",
            description = "Postre frío con Orasì Barista, una alternativa vegetal para cafés y restaurantes.",
            category = "Postres",
            chefName = "Equipo Master Martini",
            durationSeconds = 0,
            durationFormatted = "",
            youtubeVideoId = "CdSAJaKY1cY",
            difficulty = "Básico",
            recommendedFor = listOf(BusinessType.FOOD_SERVICE, BusinessType.PANADERIA_PASTELERIA),
            technicalTips = listOf(
                "Hidrata la gelatina antes de mezclar.",
                "Deja reposar en frío antes de desmoldar."
            ),
            productsUsed = listOf()
        )
    )
}
