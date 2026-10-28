# Club Master Martini - Aplicación Móvil de Capacitación Técnica y Relacionamiento B2B 
**Socio Formador:** Master Martini Chile SPA (Unigrà)  

---

## Descripción del Proyecto

Aplicación móvil nativa en **Android (Kotlin + Jetpack Compose Material 3)** diseñada para conectar a panaderos, pasteleros, chocolateros y profesionales gastronómicos con contenidos técnicos de alto valor y con el catálogo oficial de insumos de **Master Martini Chile SPA** (Caravella, Decor Up, Máster Melange, FiorFiore, Ariba).

La aplicación resuelve de forma concreta la necesidad del socio formador de:
1. Brindar **capacitaciones técnicas gratuitas** a clientes y prospectos mediante lecciones audiovisuales paso a paso.
2. Segmentar a los clientes según su **tipo de negocio** (Panadería y Pastelería, Chocolatería, Gelatería, Food Service, Retail), alineado a las categorías de mastermartini.cl.
3. Abrir **oportunidades comerciales reales** mediante la solicitud directa de muestras gratuitas de insumos desde cada video técnico.
4. Adaptarse a las particularidades de uso en cocinas y obradores, garantizando accesibilidad para **usuarios mayores de 50 años** (letra grande, alto contraste, áreas táctiles amplias).

---

## Requerimientos Evaluación 2 Implementados

| Requerimiento | Archivo / Componente | Descripción |
| :--- | :--- | :--- |
| **1. Login** | `ui.screens.LoginScreen.kt` | Autenticación con email/contraseña, validaciones en tiempo real, opción "Recordarme" con persistencia local y **3 accesos rápidos Demo** (Pastelería, Chocolatería, Panadería) para prueba inmediata del evaluador. |
| **2. Pantalla de Registro** | `ui.screens.RegisterScreen.kt` | Onboarding completo que captura datos personales, RUT comercial, dirección de despacho, segmentación por rubro gastronómico, tamaño de negocio, encuesta de intereses y switch de accesibilidad. |
| **3. Menú Principal (Home)** | `ui.screens.HomeScreen.kt` | Header personalizado con insignia del rubro, barra de búsqueda en tiempo real, carrusel "Recomendado para tu Negocio", filtros por línea de producto, tarjetas con duración, dificultad, chef e insumos asociados, y bottom navigation bar. |
| **4. Visualización de Video** | `ui.screens.VideoDetailScreen.kt` | Miniatura oficial con botón de play que carga el reproductor embebido de YouTube (videos reales del canal @mastermartinichile), tips técnicos del chef, lista de insumos Master Martini requeridos con SKU, descarga de ficha técnica PDF y modal comercial para **Solicitar Muestra Gratuita**. |
| **5. Perfil de Usuario** | `ui.screens.ProfileScreen.kt` | Tarjeta de identificación con RUT de empresa verificado, métricas de actividad (cursos vistos, muestras pedidas, favoritos), historial de muestras solicitadas con estado de despacho, edición de datos y **Modo Lectura Aumentada (Senior / Taller)**. |
| **Catálogo de Insumos** | `ui.screens.ProductCatalogScreen.kt` | Pestaña dedicada con todos los insumos Master Martini (Caravella, Decor Up, FiorFiore, Máster), especificaciones y solicitud de muestras. |
| **Favoritos / Guardados** | `ui.screens.SavedLessonsScreen.kt` | Pestaña para acceder rápidamente a las recetas guardadas sin necesidad de buscarlas de nuevo. |

---

## Identidad visual y contenido

- **Pantallas nuevas:** Splash, Login con hero fotográfico y acceso rápido por rubro, Home con saludo y lección destacada, y **Vista Marketing** (Perfil > "Ver panel de marketing") con leads por rubro, muestras por estado y videos más vistos. Sus cifras son de demostración y la pantalla lo avisa.
- **Foto del login:** fotografía de Unsplash (licencia Unsplash, uso libre), incluida en `res/drawable-nodpi/login_hero.jpg`. Reemplazable por una foto propia de producto.
- **Íconos de rubro:** set Tabler Icons (licencia MIT), convertidos a `res/drawable/ic_biz_*.xml`: pan, pastel, chocolate, helado, gorro de chef y tienda.
- **Logo:** PNG del sitio escalado 4x (única versión disponible). Reemplazar por SVG o PNG en alta resolución cuando Master Martini lo entregue (`res/drawable-nodpi/logo_master_martini.png`).

- **Paleta:** verde `#004D42` (base), mostaza `#C1A01D` (medido del logo) (acento único) y verde claro `#65BC7B` solo para estados. Tomada del logo y del sitio mastermartini.cl. Tema claro fijo, alto contraste.
- **Tipografía:** Roboto (la del sitio). El **Modo Lectura Aumentada** escala todo el texto 25%.
- **Videos:** 10 lecciones con ID y miniatura reales del canal oficial de YouTube. Descripciones, tips, dificultad, insumos y SKU son **datos de ejemplo**. La duración se muestra solo si se conoce.
- **Pendiente de autorización:** el uso de logo y videos del socio formador debe quedar registrado como "depurado y autorizado" según el caso. La app no incluye el archivo del logo. Tono sobrio y corporativo: sin recursos folclóricos ni adornos de bandera.
- **Por validar con el socio:** líneas Ariba, Decor Up, FiorFiore y Mirall (vienen del informe, no del canal).

## Credenciales de Prueba (Demo Rápida)

Para comodidad del docente o evaluador, la pantalla de Login incluye botones de 1 toque para autocompletar credenciales:

1. **Pastelería:** `pasteleria@mastermartini.cl` / Clave: `123456`  
   *(Pastelería y Café La Fontana · Foco: Cremas Decor Up, Glaseados Mirall)*
2. **Chocolatería:** `chocolateria@mastermartini.cl` / Clave: `123456`  
   *(Chocolates Cacao Real · Foco: Discos Caravella Cover Dark, Ariba 60%)*
3. **Panadería:** `panaderia@mastermartini.cl` / Clave: `123456`  
   *(Panadería San Jorge · Modo senior activado · Foco: Margarinas Máster Melange)*

*Nota: el login valida de verdad: solo entran las 3 cuentas demo o un usuario registrado en la sesión actual. Con otra clave o correo muestra un error. Nada se guarda en el dispositivo (ni usuarios ni claves): al cerrar la app los registros nuevos se pierden.*

---

## Arquitectura Backend & Supabase

En cumplimiento de la pauta de evaluación (*"https://supabase.com/ posible BD, otra opcion de AWS"*):

- **Archivo de Configuración:** `data.repository.SupabaseConfig.kt`
- **Endpoints de Microservicios:** Mapeados a PostgREST (`perfiles_usuarios`, `cursos_tecnicos`, `lecciones_videos`, `insumos_master_martini`, `solicitudes_muestras`).
- **Persistencia Local:** `MasterMartiniRepository.kt` gestiona la persistencia local robusta mediante `SharedPreferences` y State Flow, asegurando que los usuarios creados, lecciones completadas, favoritos y solicitudes de muestras permanezcan guardados entre aperturas de la app.

---

## Compilación y Generación del APK

El proyecto fue compilado y verificado con éxito:

```bash
# Compilación del APK Debug:
sh gradlew assembleDebug

# Ubicación del archivo APK generado:
app/build/outputs/apk/debug/app-debug.apk
```
