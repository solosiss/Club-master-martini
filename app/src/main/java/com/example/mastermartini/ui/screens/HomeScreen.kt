package com.example.mastermartini.ui.screens

import androidx.compose.material.icons.filled.Mic
import com.example.mastermartini.ui.components.rememberVoiceSearch
import com.example.mastermartini.ui.components.StatusBarScrim
import androidx.compose.material.icons.filled.FavoriteBorder
import com.example.mastermartini.data.model.SampleRequest
import com.example.mastermartini.data.model.BusinessType
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.semantics.Role
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.filled.SearchOff
import com.example.mastermartini.ui.components.EmptyState
import com.example.mastermartini.ui.components.LessonThumbnail
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermartini.data.model.Lesson
import com.example.mastermartini.data.model.User
import com.example.mastermartini.ui.theme.*
import com.example.mastermartini.ui.theme.BakerySurface
import com.example.mastermartini.ui.theme.MartiniGreenDark
import com.example.mastermartini.ui.theme.MartiniGreenMid
import com.example.mastermartini.ui.theme.MartiniGreen
import com.example.mastermartini.ui.theme.MartiniGold
import com.example.mastermartini.ui.theme.TextPrimary
import com.example.mastermartini.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    user: User,
    lessons: List<Lesson>,
    recommendedLessons: List<Lesson>,
    onSelectLesson: (Lesson) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onNavigateToCatalog: () -> Unit,
    sampleRequests: List<SampleRequest> = emptyList(),
    currentTab: Int = 0,
    onSelectTab: (Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<BusinessType?>(null) }
    var showNotices by remember { mutableStateOf(false) }
    val searchFocus = remember { FocusRequester() }
    val startVoice = rememberVoiceSearch { searchQuery = it }
    val pendingSamples = sampleRequests.filter { it.status != "Despachada" }

    val filteredLessons = lessons.filter { lesson ->
        val matchesType = selectedType == null || lesson.recommendedFor.contains(selectedType)
        val matchesQuery = searchQuery.isBlank() ||
                lesson.title.contains(searchQuery, ignoreCase = true) ||
                lesson.description.contains(searchQuery, ignoreCase = true) ||
                lesson.productsUsed.any { it.name.contains(searchQuery, ignoreCase = true) }
        matchesType && matchesQuery
    }

    if (showNotices) {
        AlertDialog(
            onDismissRequest = { showNotices = false },
            title = { Text("Novedades") },
            text = {
                if (sampleRequests.isEmpty()) {
                    Text("Aún no has pedido muestras. Pide una desde cualquier video o desde el catálogo.")
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        sampleRequests.forEach { Text("${it.productName}: ${it.status}", style = MaterialTheme.typography.bodyLarge) }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showNotices = false }, modifier = Modifier.heightIn(min = 56.dp)) { Text("Cerrar") }
            }
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = currentTab == 0,
                    onClick = { onSelectTab(0) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio", fontSize = 12.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MartiniGreen,
                        selectedTextColor = MartiniGreen,
                        indicatorColor = MartiniGreenTint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = currentTab == 1,
                    onClick = { onSelectTab(1) },
                    icon = { Icon(Icons.Default.GridView, contentDescription = "Catálogo") },
                    label = { Text("Catálogo", fontSize = 12.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MartiniGreen,
                        selectedTextColor = MartiniGreen,
                        indicatorColor = MartiniGreenTint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = currentTab == 2,
                    onClick = { onSelectTab(2) },
                    icon = {
                        BadgedBox(badge = {
                            if (user.favoriteLessonIds.isNotEmpty()) {
                                Badge(containerColor = MartiniGold) {
                                    Text(text = user.favoriteLessonIds.size.toString(), color = MartiniGreenDark)
                                }
                            }
                        }) {
                            Icon(Icons.Default.Favorite, contentDescription = "Guardados")
                        }
                    },
                    label = { Text("Guardados", fontSize = 12.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MartiniGreen,
                        selectedTextColor = MartiniGreen,
                        indicatorColor = MartiniGreenTint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = currentTab == 3,
                    onClick = { onSelectTab(3) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Mi Perfil") },
                    label = { Text("Perfil", fontSize = 12.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MartiniGreen,
                        selectedTextColor = MartiniGreen,
                        indicatorColor = MartiniGreenTint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(BakeryWarmBackground)
            ) {
                // Header con bienvenida corporativa y badge de rubro
                item {
                    HomeHeader(
                        user = user,
                        hasNotice = pendingSamples.isNotEmpty(),
                        onSearch = { searchFocus.requestFocus() },
                        onBell = { showNotices = true }
                    )
                }

                // Barra de Búsqueda
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Buscar", fontSize = 16.sp, color = TextSecondary) },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = null, tint = MartiniGreen)
                            },
                            trailingIcon = {
                                IconButton(onClick = startVoice, modifier = Modifier.size(56.dp)) {
                                    Icon(Icons.Default.Mic, contentDescription = "Buscar por voz", tint = MartiniGreen)
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(56.dp).focusRequester(searchFocus),
                            shape = RoundedCornerShape(28.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = MartiniGreen,
                                unfocusedBorderColor = OutlineNeutral
                            )
                        )
                    }
                }

                // Chips Horizontales de Categorías
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        (listOf<BusinessType?>(null) + BusinessType.entries).forEach { type ->
                            val isSelected = selectedType == type
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) MartiniGreen else Color.White,
                                border = if (isSelected) null else BorderStroke(1.dp, OutlineNeutral),
                                modifier = Modifier.height(40.dp).clickable(role = Role.Button) { selectedType = type }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = type?.chipLabel() ?: "Todos",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isSelected) Color.White else TextPrimary,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Banner Recomendado para el Negocio (Personalización clave del caso)
                if (searchQuery.isBlank() && selectedType == null) {
                    item {
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp)
                            ) {
                                items(recommendedLessons) { lesson ->
                                    FeaturedCard(
                                        modifier = Modifier.fillParentMaxWidth(0.918f),
                                        lesson = lesson,
                                        onClick = { onSelectLesson(lesson) }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Banner Promocional para pedir muestras de insumos Master Martini
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                                .clickable(role = Role.Button) { onNavigateToCatalog() },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MartiniGreen)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = "Solicita muestras técnicas gratuitas",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Prueba en tu cocina la calidad de los insumos Master Martini, sin costo.",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MartiniGreenPale
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                // Título Catálogo General
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedType?.displayName ?: "Videos y Cursos Técnicos",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MartiniGreenDark
                        )
                        Text(
                            text = "${filteredLessons.size} lecciones",
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                    }
                }

                if (filteredLessons.isEmpty()) {
                    item {
                        EmptyState(
                            icon = Icons.Default.SearchOff,
                            title = "Sin resultados",
                            message = "Prueba con otra palabra o cambia el filtro de línea."
                        )
                    }
                }

                // Lista de Lecciones
                items(filteredLessons) { lesson ->
                    val isFavorite = user.favoriteLessonIds.contains(lesson.id)
                    val isCompleted = user.completedLessonIds.contains(lesson.id)
                    LessonCard(
                        lesson = lesson,
                        isFavorite = isFavorite,
                        isCompleted = isCompleted,
                        onCardClick = { onSelectLesson(lesson) },
                        onToggleFavorite = { onToggleFavorite(lesson.id) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            StatusBarScrim(Modifier.align(Alignment.TopStart))
        }
    }
}

@Composable
private fun HomeHeader(
    user: User,
    hasNotice: Boolean,
    onSearch: () -> Unit,
    onBell: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MartiniGreen)
            .statusBarsPadding()
            .padding(start = 16.dp, end = 8.dp, bottom = 16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Negocio del usuario arriba, a la izquierda de la lupa y la campana
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                Surface(shape = RoundedCornerShape(16.dp), color = MartiniGreenPale, modifier = Modifier.height(32.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = user.businessName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MartiniGreenDark,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
            }
            IconButton(onClick = onSearch, modifier = Modifier.size(56.dp)) {
                Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
            }
            IconButton(onClick = onBell, modifier = Modifier.size(56.dp)) {
                BadgedBox(badge = { if (hasNotice) Badge(containerColor = MartiniGold) }) {
                    Icon(Icons.Default.Notifications, contentDescription = if (hasNotice) "Novedades, hay pendientes" else "Novedades", tint = Color.White)
                }
            }
        }
        Text(
            text = "Hola, " + user.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(end = 12.dp)
        )
    }
}

@Composable
private fun FeaturedCard(
    lesson: Lesson,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(16.dp))
            .clickable(role = Role.Button, onClickLabel = "Ver " + lesson.title) { onClick() }
    ) {
        LessonThumbnail(lesson, Modifier.matchParentSize(), highRes = true)
        // Scrim: transparente a negro (alpha 0.7) en el 40% inferior
        Box(
            Modifier.matchParentSize().background(
                Brush.verticalGradient(0.6f to Color.Transparent, 1f to Color.Black.copy(alpha = 0.7f))
            )
        )
        Text(
            text = lesson.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.BottomStart).padding(start = 16.dp, bottom = 16.dp, end = 88.dp)
        )
        Surface(
            shape = CircleShape,
            color = MartiniGold,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp).size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = MartiniGreenDark, modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
fun LessonCard(
    lesson: Lesson,
    isFavorite: Boolean,
    isCompleted: Boolean,
    onCardClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(role = Role.Button) { onCardClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BakerySurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 110.dp, height = 80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    LessonThumbnail(lesson, Modifier.fillMaxSize())
                    DurationBadge(lesson, Modifier.align(Alignment.BottomEnd).padding(4.dp))
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = lesson.category,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MartiniGreen
                        )

                        IconButton(
                            onClick = onToggleFavorite,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Guardar",
                                tint = if (isFavorite) ErrorRed else TextMuted,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Text(
                        text = lesson.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = lesson.chefName,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Insumos Master Martini asociados
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MartiniGreenTint
                        ) {
                            Text(
                                text = lesson.productsUsed.firstOrNull()?.name ?: "Insumos Master Martini",
                                fontSize = 12.sp,
                                color = MartiniGreenDark,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Completado",
                                tint = SuccessGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/** Duración sobre la miniatura; se omite cuando es desconocida (0). */
@Composable
private fun DurationBadge(lesson: Lesson, modifier: Modifier = Modifier) {
    if (lesson.durationSeconds <= 0) return
    Surface(shape = RoundedCornerShape(4.dp), color = Color.Black.copy(alpha = 0.75f), modifier = modifier) {
        Text(
            text = lesson.durationFormatted,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

/** Nombres cortos para los chips de filtro, como en el diseño. */
private fun BusinessType.chipLabel() = when (this) {
    BusinessType.PANADERIA_PASTELERIA -> "Panadería"
    else -> displayName
}
