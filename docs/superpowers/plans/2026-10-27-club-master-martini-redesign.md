# Club Master Martini: Rediseño de marca + simplificación Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Alinear la app Android a la identidad real de Master Martini, cargar videos reales de su canal de YouTube y reducir duplicación sin cambiar el comportamiento.

**Architecture:** Compose Material 3 existente. Se centralizan tokens de marca en `ui/theme`, se extraen componentes compartidos a `ui/components`, y el contenido real vive en `MasterMartiniData`. Las miniaturas se derivan del ID de YouTube.

**Tech Stack:** Kotlin, Jetpack Compose M3, `material-icons-extended` (ya presente), Coil (`io.coil-kt:coil-compose:2.7.0`, nueva), JUnit4.

**Spec:** Aprobada en chat el 2026-10-27 (sin documento aparte). Decisiones: preservar y pulir, paleta verde+dorado, reemplazar videos por reales, rubros alineados al sitio, refactor sin cambio de comportamiento, verificación con gradle.

## Global Constraints

- Comando de build: `sh gradlew assembleDebug` desde la raíz del proyecto (`gradlew` no es ejecutable). Tests: `sh gradlew testDebugUnitTest`.
- Paleta: base verde `#004D42`, único acento mostaza `#D4AF37` (existente `MartiniGold`), verde claro `#65BC7B` solo para éxito. Prohibido `Color(0xFF...)` literal en pantallas: todo sale de `MaterialTheme.colorScheme` o `ui/theme/Color.kt`.
- Tipografía: `FontFamily.Default` (Roboto en Android, la del sitio). Pesos 500 y 600 en jerarquías. Cuerpo mínimo 16sp, 20sp con `largeTextEnabled`.
- Sin emojis en la UI ni en datos mostrados: íconos de `androidx.compose.material.icons`.
- Targets táctiles >= 56dp en botones y filas clicables. Contraste WCAG AA.
- Cero em-dash (`—`) en textos visibles.
- Datos de usuario y SKU son ficticios; tips y dificultad de lecciones son texto de ejemplo.
- No hacer commits ni push (los hace el usuario). Ningún subagente commitea.
- Subagentes: Sonnet 5.5, esfuerzo medium.
- No cambiar `package`, rutas de navegación ni claves de `SharedPreferences`.

## Review Focus

- Sin red: miniatura no carga, debe mostrar el placeholder verde con ícono, nunca crashear.
- `largeTextEnabled` activo: ningún texto se corta ni solapa en Login, Home y Video.
- Usuario guardado con rubro antiguo en `SharedPreferences`: `BusinessType.fromDisplayName` cae a un valor válido, no crashea.
- Búsqueda en Home con 0 resultados: muestra estado vacío, no pantalla en blanco.
- Lección sin insumos asociados: la sección de insumos se omite sin error.

---

## File Structure

- Modify `ui/theme/Color.kt`, `Theme.kt`, `Type.kt`: tokens de marca y escala senior.
- Create `ui/components/LessonThumbnail.kt`, `StateViews.kt`, `BrandHeader.kt`: piezas compartidas.
- Modify `data/model/Models.kt`, `data/repository/MasterMartiniData.kt`: rubros, lecciones reales.
- Modify las 7 pantallas en `ui/screens/`.
- Test `app/src/test/java/com/example/mastermartini/ContentTest.kt`.
- Modify `README.md` al final.

### Task 1: Tokens de marca (tema)

**Files:**
- Modify: `ui/theme/Color.kt`, `ui/theme/Theme.kt`, `ui/theme/Type.kt`

**Interfaces:**
- Produces: en `Color.kt`, `MartiniGreen = Color(0xFF004D42)`, `MartiniGreenDark`, `MartiniGreenLight = Color(0xFF65BC7B)`, `MartiniGold` (existente); se eliminan `MartiniBlue*` y `MartiniChocolate*`. `MastermartiniTheme(darkTheme: Boolean, largeText: Boolean = false, content)` (nuevo parámetro `largeText`).

- [ ] **Step 1:** Reemplazar `MartiniBlue*` por los verdes en `Color.kt`; derivar `GreenDark` (~`#00332B`) y mantener fondos neutros cálidos existentes (`BakeryWarmBackground`).
- [ ] **Step 2:** En `Theme.kt`, `primary = MartiniGreen`, `secondary = MartiniGold`, esquema oscuro con fondo `MartiniGreenDark`. `statusBarColor` según esquema.
- [ ] **Step 3:** En `Type.kt`, escala con cuerpo 16sp, títulos peso SemiBold, y factor 1.25x aplicado vía `largeText` (escala `Typography` copiando tamaños).
- [ ] **Step 4:** Build: `sh gradlew assembleDebug`. Esperado: fallan solo las referencias a `MartiniBlue*`/`MartiniChocolate*` en pantallas (se corrigen en tareas 5-9); si prefieres build verde ahora, mantener alias temporales y borrarlos en Task 10.

### Task 2: Rubros y modelo

**Files:**
- Modify: `data/model/Models.kt` (BusinessType, Lesson)
- Test: `app/src/test/.../ContentTest.kt`

**Interfaces:**
- Produces: `enum BusinessType { PANADERIA_PASTELERIA("Panadería y Pastelería"), CHOCOLATERIA("Chocolatería"), GELATERIA("Gelatería"), FOOD_SERVICE("Food Service"), RETAIL("Retail") }` con propiedad `icon: ImageVector` (sustituye `icon: String` emoji), `displayName`, `description`. `BusinessType.fromDisplayName(name)` conserva fallback al primer valor. `Lesson` pierde `videoUrl` y `thumbnailUrl`; gana `val thumbnailUrl: String get() = "https://i.ytimg.com/vi/$youtubeVideoId/hqdefault.jpg"`. `Product.iconEmoji` se elimina.
- Consumes: nada.

- [ ] **Step 1: Test** `fromDisplayName_unknown_fallsBack()`: `assertEquals(BusinessType.PANADERIA_PASTELERIA, BusinessType.fromDisplayName("Pastelería"))`. Y `thumbnail_isDerivedFromId()` con un `Lesson` de prueba.
- [ ] **Step 2:** `sh gradlew testDebugUnitTest --tests '*ContentTest*'`. Esperado: FAIL (no compila).
- [ ] **Step 3:** Implementar cambios del modelo; grep de `PASTELERIA`, `HELADERIA`, `PANADERIA`, `RESTAURANTE_CASINO`, `iconEmoji`, `videoUrl` en todo `app/src` y actualizar usos (incluidos usuarios demo en `MasterMartiniData`/`Repository`).
- [ ] **Step 4:** Tests pasan.

### Task 3: Contenido real y test de datos

**Files:**
- Modify: `data/repository/MasterMartiniData.kt` (LESSONS, PRODUCTS)
- Test: `ContentTest.kt`

**Interfaces:**
- Consumes: modelo de Task 2.
- Produces: `MasterMartiniData.LESSONS: List<Lesson>` con 10 lecciones reales (IDs de YouTube validados abajo); `PRODUCTS` alineados a líneas vistas en el canal.

Videos a usar (ID | título real | rubros sugeridos):
`Dz20QKTeK5I` Pancake de Avena y Plátano con Chef Lili Cañete | PANADERIA_PASTELERIA, RETAIL;
`v_WinlHRQt0` Tips y Usos del Chocolate con Chef Lili Cañete | CHOCOLATERIA;
`C1HaDB9US_Y` ¡Qué fácil es con Chocolate Caravella! | CHOCOLATERIA, PANADERIA_PASTELERIA;
`ZwOkabO177M` Torta Chocolate Avellana (Caravella Deco) | PANADERIA_PASTELERIA, CHOCOLATERIA;
`1U1twaw6bP0` ¿Cómo utilizar crema pastelera, merengue o crema chantilly EN POLVO? (Delice) | PANADERIA_PASTELERIA;
`3n-h0n7Ttks` ¿Cómo utilizar la crema Vegetal? (Bravo Crem) | PANADERIA_PASTELERIA, FOOD_SERVICE;
`R2N_oOG0xI8` Helado Artesanal Frutos del Bosque (Gelato Martini) | GELATERIA;
`KhhA9pAJZxA` Helado Artesanal de Avellana (Base Blanca Martini Gelato) | GELATERIA;
`Qwv-Uxbivqw` Lomo strogonoff (Bravo Crem Cocina) | FOOD_SERVICE;
`CdSAJaKY1cY` Panna Cotta (Orasì Barista) | FOOD_SERVICE, PANADERIA_PASTELERIA.

- [ ] **Step 1: Tests** en `ContentTest`: `lessons_haveValidYoutubeIds()` (cada `youtubeVideoId` coincide con `^[A-Za-z0-9_-]{11}$` y no es `dQw4w9WgXcQ`); `lessons_coverEveryBusinessType()` (todo `BusinessType` aparece en `recommendedFor` de alguna lección); `lessons_haveUniqueIds()`.
- [ ] **Step 2:** Ejecutar tests. Esperado: FAIL.
- [ ] **Step 3:** Reescribir `LESSONS` con los 10 videos. Títulos de YouTube tal cual; `chefName = "Chef Lili Cañete"` solo donde el título lo dice, `"Equipo Master Martini"` en el resto; `description`, `technicalTips`, `difficulty` breves y marcados como ejemplo en el comentario de cabecera del objeto. `durationSeconds/durationFormatted`: marcar con `0`/`""` y ocultar la duración en UI si es 0 (no inventar duraciones). Ajustar `PRODUCTS` para que `line` use solo: Caravella, Bravo Crem, Orasì, Delice, Master Shine, Martini Gelato; mantener `FiorFiore`/`Ariba` solo porque vienen del informe, con comentario "por validar con el socio".
- [ ] **Step 4:** Tests pasan.

### Task 4: Componentes compartidos

**Files:**
- Create: `ui/components/LessonThumbnail.kt`, `ui/components/StateViews.kt`, `ui/components/BrandHeader.kt`
- Modify: `app/build.gradle.kts` (+ `implementation("io.coil-kt:coil-compose:2.7.0")`)

**Interfaces:**
- Produces:
  - `@Composable fun LessonThumbnail(lesson: Lesson, modifier: Modifier = Modifier)`: `AsyncImage` con `contentScale = Crop`, fondo `primaryContainer` e ícono `PlayCircle` mientras carga o si falla.
  - `@Composable fun EmptyState(icon: ImageVector, title: String, message: String, modifier: Modifier = Modifier)`
  - `@Composable fun ErrorState(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier)`
  - `@Composable fun BrandHeader(title: String, modifier: Modifier = Modifier)`: franja verde con barra tricolor (verde `#009246`, blanco, rojo `#CE2B37`) de 3dp bajo el título; usa solo colores del tema más esas dos constantes privadas.
- Nota: la dependencia Coil requiere red en el primer build (`sh gradlew assembleDebug` sin `--offline`). Si no hay red, fallback: omitir Coil y usar `LessonThumbnail` solo con el placeholder, declarándolo.

- [ ] **Step 1:** Añadir Coil, sincronizar y compilar.
- [ ] **Step 2:** Crear los tres archivos con las firmas anteriores.
- [ ] **Step 3:** Build verde.

### Task 5: Login y Registro

**Files:**
- Modify: `ui/screens/LoginScreen.kt` (3 emojis, `DemoChip`), `ui/screens/RegisterScreen.kt` (`SectionHeader`, `CustomInputField`)

**Interfaces:**
- Consumes: Task 1 (tema), Task 2 (`BusinessType.icon`), Task 4 (`BrandHeader`).

- [ ] **Step 1:** Login: `BrandHeader`, reemplazar emojis por íconos, chips demo a 56dp, etiquetas siempre sobre el campo, errores bajo el campo.
- [ ] **Step 2:** Registro: selector de rubro con los 5 `BusinessType` e íconos; `CustomInputField` conserva validaciones.
- [ ] **Step 3:** Quitar `Color(0xFF...)` literales; build verde.

### Task 6: Home y Guardados

**Files:**
- Modify: `ui/screens/HomeScreen.kt` (`HomeHeader`, `RecommendedCard`, `LessonCard`, gradientes por categoría L469-471, colores literales), `ui/screens/SavedLessonsScreen.kt`

**Interfaces:**
- Consumes: `LessonThumbnail`, `EmptyState`, `BusinessType`.

- [ ] **Step 1:** Reemplazar los `Brush.linearGradient` por categoría y colores literales por `LessonThumbnail` y tokens. `indicatorColor = MaterialTheme.colorScheme.primaryContainer`.
- [ ] **Step 2:** Búsqueda sin resultados y Guardados vacío usan `EmptyState`.
- [ ] **Step 3:** Ocultar duración cuando `durationSeconds == 0`.
- [ ] **Step 4:** Build verde.

### Task 7: Video

**Files:**
- Modify: `ui/screens/VideoDetailScreen.kt` (699 líneas)

- [ ] **Step 1:** Eliminar la rama de reproducción MP4 nativa (todas las lecciones son YouTube): dejar solo el reproductor `WebView` con `youtube-nocookie.com/embed/${lesson.youtubeVideoId}`; quitar `useWebViewPlayer` y el estado de seek/play que quede sin uso.
- [ ] **Step 2:** Portada con `LessonThumbnail` y botón de play grande (56dp+). Conservar tips, insumos, ficha PDF y "Solicitar Muestra".
- [ ] **Step 3:** Omitir sección de insumos si `productsUsed` está vacío.
- [ ] **Step 4:** Build verde.

### Task 8: Perfil y Catálogo

**Files:**
- Modify: `ui/screens/ProfileScreen.kt` (`MetricCard`, `ProfileInfoRow`), `ui/screens/ProductCatalogScreen.kt`

- [ ] **Step 1:** Sustituir emoji de producto por ícono según `category`; tokens de tema; estado vacío del historial de muestras con `EmptyState`.
- [ ] **Step 2:** El interruptor de modo senior llama a `MastermartiniTheme(largeText = ...)` desde `MainActivity.ClubMasterMartiniApp` (conectar con `User.largeTextEnabled`).
- [ ] **Step 3:** Build verde.

### Task 9: Simplificación del código

**Files:**
- Modify: `MainActivity.kt`, `data/repository/MasterMartiniRepository.kt`, pantallas

- [ ] **Step 1:** Grep de funciones, imports y propiedades sin uso (`@Suppress`, imports huérfanos, `SupabaseConfig` solo referenciado por el README: conservar, es requisito de la evaluación). Eliminar lo muerto.
- [ ] **Step 2:** Unificar duplicados entre pantallas (campos de formulario, tarjetas de métrica) hacia `ui/components` solo si aparecen en 2+ pantallas.
- [ ] **Step 3:** Build verde y tests verdes.

### Task 10: Verificación final y documentación

- [ ] **Step 1:** Borrar alias temporales (Task 1). Grep: `grep -rn "Color(0xFF" app/src/main/java/.../ui/screens` sin resultados; `grep -rn "—"` sin resultados; `grep -rn "dQw4w9WgXcQ"` sin resultados.
- [ ] **Step 2:** `sh gradlew assembleDebug testDebugUnitTest`. Esperado: BUILD SUCCESSFUL.
- [ ] **Step 3:** Invocar `web-design-guidelines` sobre `ui/` y corregir hallazgos.
- [ ] **Step 4:** Actualizar `README.md` (paleta, videos reales, rubros, nota de autorización de logo y videos).

## Self-Review

- Cobertura: paleta (T1), videos reales (T3), rubros (T2), miniaturas y estados (T4, T6, T8), senior (T1, T8), simplificación (T7, T9), verificación (T10).
- Riesgos abiertos: Coil exige red al primer build; logo e imágenes de marca no se incluyen como asset hasta que el socio los autorice (el header usa texto y barra tricolor); duraciones de video desconocidas.
