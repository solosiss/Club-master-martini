# SDD ledger — plan: docs/superpowers/plans/2026-10-27-club-master-martini-redesign.md
Pre-flight: T2 produces BusinessType/Lesson changes, T3,T5-T8 consume; T1 aliases temporales hasta T10.
Setup: Ruling: proyecto no es repo git, sin worktree ni commits (el usuario commitea) — cost if wrong: ninguno.
Setup: Ruling: manifest tenía android.intent.permission.INTERNET (inexistente); corregido a android.permission.INTERNET — sin esto no cargan videos ni miniaturas — cost: ninguno.
Task 1-4: complete (tests: sh gradlew testDebugUnitTest -> 6/6 pass; compileDebugKotlin ok)
Task 1: Ruling: renombre mecánico MartiniBluePrimary/Dark/Light -> MartiniGreen/GreenDark/GreenMid (sin alias temporales, 110 usos) — evita deuda — cost: ninguno.
Task 2: Ruling: RETAIL/GELATERIA cubiertos por videos reales; PRODUCTS conservados (ficticios) salvo línea FiorFiore Gelato->Martini Gelato.
Task 8: Ruling: tema movido dentro de ClubMasterMartiniApp para leer largeTextEnabled; el modo senior ahora sí escala la tipografía (antes solo guardaba el flag).
Task 5-10 build: sh gradlew assembleDebug testDebugUnitTest -> BUILD SUCCESSFUL, ContentTest 5/5 + Example 1/1; grep checks: 0 literales Color(0xFF) en pantallas, 0 em-dash, 0 emojis, 0 dQw4w9WgXcQ
Task 7: Ruling: reproductor nativo simulado (timer+slider) eliminado, miniatura + play grande abre WebView YouTube; durationSeconds 0 ya no divide por cero — cost: se pierde un control que no reproducía video real.
Task 8: Ruling: modo senior escala via LocalDensity.fontScale x1.25 (cubre sp fijos) en vez de LargeTypography; tema fijado en claro (darkTheme=false) porque las pantallas usan superficies claras fijas — cost: sin modo oscuro (ya estaba roto).
Task 10: web-design-guidelines (subconjunto transferible): roles en clickables, liveRegion en errores, elipsis tipográfica, enlace Ayuda muerto eliminado, Recordarme toggleable 56dp. Build+tests OK.
Final review: self-review (el usuario pidió ejecutar solo en esta sesión)
Task 10: README actualizado
Final: fixed chips de categoría de Home fijos (lecciones nuevas no filtrables) — derivados de lessons, build+tests 6/6.
Final: fixed sección Insumos vacía en lecciones sin productos — condicional isNotEmpty, build OK.
Final: minor (deferred): Ruling pendiente: catálogo Productos sigue con categorías/líneas de ejemplo; durations desconocidas.
Final: minor (deferred): sin test de UI/instrumentación; solo ContentTest sobre datos.
Repo: login estricto sin persistencia (singleton shared, ctor internal); RepositoryTest 7 + mutación detectada. Ruling: login solo correo (como POST /auth/login del informe). Registro duplicado vuelve a Login con mensaje.
Diseño: image-to-code con maqueta de Gemini (1 imagen board). Logo: PNG del sitio 180x110 escalado 4x (único disponible; mostaza medido #C1A01D). Splash + Login rehechos.
Fase diseño (Gemini): Splash, Login, Home (header/featured/chips), Vista Marketing + MarketingDemo (test RED->GREEN). Tests 16/16. Ruling: charts con una gama de verde (validate_palette: verde claro+mostaza fallaban ΔE 12.8). Ruling: Vista Marketing en tema claro (dark mode para después, según usuario).
Pendiente (no hecho): Registro por pasos, Detalle de video, Catálogo y Perfil recompuestos como la maqueta; verificación visual en dispositivo.
Final: self-review (usuario pidió solo yo).
Login hero: miniaturas de YouTube traían texto incrustado y chocaban con el logo; reemplazado por foto limpia de Unsplash (login_hero.jpg, 224KB, incluida en la app) sobre fondo oscuro HeroBackdrop. Tests 16/16.
Home según gemini-board.png: header con lupa (enfoca buscador) y campana (punto mostaza si hay muestras sin despachar, abre diálogo Novedades), chip claro del negocio, chips por rubro (BusinessType), destacada con título abajo-izq y play abajo-der sin degradado verde, nav Inicio/Catálogo/Guardados/Perfil con GridView y Favorite. Ruling: campana funcional (derivada de sampleRequests) en vez de control muerto. Memoria: mastermartini-design-reference.md. Tests 16/16.
Home v2 (corrección tras captura del usuario): fila de íconos arriba + saludo + chip píldora; chips píldora con nombres cortos; destacada 3:2 al 86% del ancho con degradado negro y play translúcido; tarjeta muestras verde lisa sin ícono; nav sin óvalo, activo en negrita, corazón relleno.
Home v3: aplicadas especificaciones numéricas del usuario (docs/design/home-spec.md): 16:9 al ancho de 358dp, play mostaza 56dp, chips 40dp/r20, buscador 56dp/r28, saludo 28sp, chip negocio 32dp/r16, nav con píldora. Ruling: mostaza #C1A01D (medido del logo) en vez de #D4AF37; chips 40dp pese a regla de 56dp. Tests 16/16.
Íconos: BusinessType.icon (Material) -> iconRes (Tabler, MIT): bread, cake, chocolate, ice-cream, chef-hat, building-store. Login: cada tarjeta con su ícono (Pastelería=cake, Chocolatería=chocolate, Panadería=bread). categoryIcon() devuelve @DrawableRes. Ruling: la web usa fotos de categoría, no íconos; se eligió un set coherente de trazo. Build+tests OK.
Volver atrás: BrandHeader con onBack (56dp, arriba-izq) en Catálogo y Guardados, que no tenían barra inferior ni salida; BackHandler: desde pestaña 1/2 vuelve a Inicio; botones de Perfil/Registro/Video unificados a 56dp. Build+tests OK.
Logo: el script verde oscuro se perdía sobre verde/oscuro. Opción A elegida por el usuario: logo original sobre fondo claro. Splash crema con texto verde; Login hero con fondo crema arriba (foto emerge debajo). DarkStatusBarIcons para íconos oscuros en barra de estado. Pendiente: pedir logo versión negativa a Master Martini. Build+tests OK.
Login X (elegido por usuario): página crema, logo original arriba, foto enmarcada r16 sin degradados, campos blancos con borde, botón mostaza, tarjetas claras con borde. Descartada opción Y (placa blanca). Build+tests OK.
Login X (elegido por el usuario): página crema, logo original arriba, foto enmarcada r16 sin degradados, campos blancos con borde, botón mostaza, tarjetas claras con borde. Descartada Y. Build+tests OK.
Login: el usuario cambió de X a Y (foto a sangre + placa blanca con logo + formulario verde). Implementado Y; HeroBackdrop restaurado. DarkStatusBarIcons ya no se usa en Login (sí en Splash). Build+tests OK.
Login revertido a la versión previa a los cambios de logo (hero oscuro HeroBackdrop 420dp, foto 330dp, logo 150dp directo, degradado 0.42/0.7/0.84). Splash queda pendiente de solución (hoy: crema opción A). Build+tests OK.
Splash opción 2 (elegida): mitad crema con logo original + mitad verde con curva (Canvas Path), sin placa. Placas descartadas por el usuario. Build+tests OK.
Login: sin descripción bajo el título; título 32sp ExtraBold (Roboto); etiquetas Bold; botón ExtraBold. Splash conserva la frase. Build+tests OK.
Login creativo (1+2+4): kicker 'BIENVENIDO AL' mostaza + título 34sp ExtraBold sobre la foto abajo-izq; logo arriba-izq; foto con zoom 5% en 12s; entrada escalonada (Reveal) del título y tarjetas; respeta ANIMATOR_DURATION_SCALE=0. Build OK.
Splash: eliminado el texto 'Club Master Martini'; queda logo + frase dorada. Build OK.
Splash: eliminada también la frase; solo logo sobre crema + curva verde. Build OK.
Home header: firma 'CLUB MASTER MARTINI' mostaza 12sp espaciada a la izquierda de lupa/campana (el logo no se lee sobre verde). Mensaje del usuario quedó cortado ('que podría ser'): pendiente su idea. Build OK.
Home header v4: sin 'CLUB MASTER MARTINI'; chip del negocio arriba-izq en la fila de lupa/campana; saludo debajo; padding inferior 24->16 (cabezal ~50dp más bajo). Build OK.
StatusBarScrim: franja verde fija bajo la barra de estado en Home, Catálogo y Guardados (las demás pantallas usan TopAppBar). Build OK.
Catálogo y Guardados: eliminados los subtítulos de la cabecera. Build OK.
Perfil: recuadros de rubro y tamaño con ícono 36dp, texto 16sp, mismo alto (IntrinsicSize.Min), color de ícono verde, texto del tamaño en TextPrimary. Build OK.
Transiciones: Crossfade 200ms entre pantallas y entre pestañas (Inicio/Catálogo/Guardados) en MainActivity; 0 ms con animaciones del sistema desactivadas. Build OK.
Transiciones: 200ms -> 130ms. Build OK.
Vista Marketing: anillo de muestras se llena (1s, FastOutSlowIn) en sentido horario y los números/porcentajes de la leyenda suben a la vez; resto estático; sin animación si el sistema las desactiva. Build OK.
Vista Marketing: barras de leads crecen escalonadas (800ms, 90ms de desfase) con números y total subiendo en sincronía; videos más vistos entran con Reveal escalonado. Build OK.
Marketing: barras de leads animadas a la vez (un solo Animatable, 1000ms) con números y total sincronizados; sin desfase. Build OK.
Video: causa raíz del reproductor en blanco = loadDataWithBaseURL('https://www.youtube.com') -> YouTube responde error 152-4 (EMBEDDER_IDENTITY_DENIED); sin Referer sería 153. Fix: origen propio https://<packageName>/ + origin param; verificado con las respuestas reales de youtube-nocookie para los 10 videos (0 errores). Sin probar en dispositivo. Build OK.
Video: añadido registro de diagnóstico solo en debug (Logcat 'YouTubePlayer' + WebView debugging para chrome://inspect). Build OK.
Login: contraste de 'BIENVENIDO AL' corregido (degradado oscuro en la base de la foto, 14sp), verificado en el emulador del usuario por captura. Ícono de la app reemplazado por el logo sobre crema (adaptativo + legacy PNG). Video negro en el emulador: causa = hw.gpu.enabled=no (el video corre: playing, 640x360, sin error de YouTube); el código está bien. Build+tests OK.
VIDEO (causa real, corrige diagnóstico previo): el iframe de YouTube medía 0px de alto porque en el WebView height:100%/100vh se resuelven a 0 (html/body computed height 0px aunque clientHeight=230). Audio sonaba, 704 frames decodificados. Fix: alto fijo en px (PlayerHeight=230.dp escrito en el HTML). Verificado en Samsung A12 real (SM-A125F, Android 12, WebView 121): video visible con controles. La hipótesis 'GPU por software del emulador' y la de Crossfade quedaron descartadas. Build+tests OK.
Funciones nativas: (1) compartir receta: buildShareText + shareLesson con enlace real youtu.be, 'Por <chef>'; (2) voz: rememberVoiceSearch (RecognizerIntent es-CL) con micrófono en búsqueda de Home y Catálogo; (3) foto de perfil: Photo Picker + cámara del sistema (FileProvider), ProfilePhotoStore en files/profile (excluido de backup), limpieza de huérfanas, Quitar foto. Pruebas: NativeHelpersTest 8; verificado en emulador: dictado abre en es-CL, chooser de compartir, galería, cámara, persistencia tras reinicio, refresco en vivo (fix: setParameter version), quitar foto. NO verificado: dictado con voz real y el flujo en el Samsung (bloqueado con PIN). Ruling: ACCESS_NETWORK_STATE typo corregido. Tips/insumos son datos de ejemplo: riesgo al compartir (documentado).
