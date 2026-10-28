package com.example.mastermartini.ui.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermartini.data.model.SampleRequest
import com.example.mastermartini.data.model.User
import com.example.mastermartini.ui.theme.*
import com.example.mastermartini.ui.theme.BakerySurface
import com.example.mastermartini.ui.theme.MartiniGreenDark
import com.example.mastermartini.ui.theme.MartiniGreenMid
import com.example.mastermartini.ui.theme.MartiniGreen
import com.example.mastermartini.ui.theme.MartiniGold
import com.example.mastermartini.ui.theme.TextPrimary
import com.example.mastermartini.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: User,
    sampleRequests: List<SampleRequest>,
    onUpdateUser: (User) -> Unit,
    onLogout: () -> Unit,
    onOpenMarketing: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showLogoutConfirm by remember { mutableStateOf(false) }
    var largeTextMode by remember { mutableStateOf(user.largeTextEnabled) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Perfil del Profesional",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack, modifier = Modifier.size(56.dp)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showLogoutConfirm = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar Sesión",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MartiniGreen
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BakeryWarmBackground)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Tarjeta de Identificación del Usuario y Negocio
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = BakerySurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MartiniGreen,
                            modifier = Modifier.size(60.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = user.name.take(2).uppercase(),
                                    color = MartiniGold,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = user.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verificado",
                                    tint = MartiniGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Text(
                                text = user.businessName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MartiniGold
                            )

                            Text(
                                text = "RUT: " + user.businessRut,
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Rubro y tamaño del negocio: recuadros del mismo alto, ícono y texto grandes
                    Row(
                        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MartiniGreenTint,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painterResource(user.businessType.iconRes),
                                    contentDescription = null,
                                    tint = MartiniGreen,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = user.businessType.displayName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MartiniGreen
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = BakerySurfaceVariant,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = user.businessSize.displayName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Métricas de Actividad en la Plataforma
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    number = user.completedLessonIds.size.toString(),
                    label = "Cursos Vistos",
                    icon = Icons.Default.CheckCircle,
                    color = SuccessGreen
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    number = sampleRequests.size.toString(),
                    label = "Muestras Pedidas",
                    icon = Icons.Default.ShoppingBag,
                    color = MartiniGreen
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    number = user.favoriteLessonIds.size.toString(),
                    label = "Guardados",
                    icon = Icons.Default.Bookmark,
                    color = MartiniGold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Datos de Contacto y Despacho
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BakerySurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Información Comercial",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MartiniGreen
                        )

                        TextButton(onClick = { showEditDialog = true }) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Editar", fontSize = 14.sp)
                        }
                    }

                    ProfileInfoRow(icon = Icons.Default.Email, title = "Correo", value = user.email)
                    ProfileInfoRow(icon = Icons.Default.Phone, title = "Teléfono", value = user.phone)
                    ProfileInfoRow(icon = Icons.Default.LocationOn, title = "Despacho", value = user.address)
                    ProfileInfoRow(icon = Icons.Default.Business, title = "Intereses", value = user.interests.joinToString(", "))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Historial de Muestras Técnicas Solicitadas (Requisito comercial clave)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BakerySurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Mis Solicitudes de Muestras Master Martini",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MartiniGreen
                    )
                    Text(
                        text = "Seguimiento de envíos para pruebas técnicas en tu taller:",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )

                    if (sampleRequests.isEmpty()) {
                        Text(
                            text = "Aún no has solicitado muestras de insumos. Explora las lecciones técnicas y solicita muestras gratuitas.",
                            fontSize = 14.sp,
                            color = TextSecondary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        sampleRequests.forEach { sample ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = BakerySurfaceVariant,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = sample.productName,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                        Text(
                                            text = "Fecha: ${sample.requestedAt} · Destino: ${sample.deliveryAddress.take(24)}…",
                                            fontSize = 12.sp,
                                            color = TextSecondary
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = if (sample.status == "Despachada") SuccessSurface else GoldSurface
                                    ) {
                                        Text(
                                            text = sample.status,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (sample.status == "Despachada") SuccessGreen else GoldInk,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Configuración de Accesibilidad para Usuarios Mayores de 50 años
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = GoldSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MartiniGold)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Modo Lectura Aumentada",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MartiniGreenDark
                        )
                        Text(
                            text = "Diseñado para facilitar la visualización en cocinas u obradores y para profesionales mayores de 50 años.",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                    Switch(
                        checked = largeTextMode,
                        onCheckedChange = {
                            largeTextMode = it
                            onUpdateUser(user.copy(largeTextEnabled = it))
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MartiniGold,
                            checkedTrackColor = MartiniGreen
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Acceso al panel comercial (demostración para los dueños)
            Button(
                onClick = onOpenMarketing,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MartiniGold, contentColor = MartiniGreenDark)
            ) {
                Icon(Icons.Default.BarChart, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Ver panel de marketing", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón de Cerrar Sesión
            OutlinedButton(
                onClick = { showLogoutConfirm = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed)
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Cerrar Sesión", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Modal para Editar Perfil
    if (showEditDialog) {
        var editName by remember { mutableStateOf(user.name) }
        var editPhone by remember { mutableStateOf(user.phone) }
        var editAddress by remember { mutableStateOf(user.address) }
        var editBizName by remember { mutableStateOf(user.businessName) }
        var editType by remember { mutableStateOf(user.businessType) }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar Perfil Comercial", fontWeight = FontWeight.Bold, color = MartiniGreen) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editBizName,
                        onValueChange = { editBizName = it },
                        label = { Text("Nombre de Fantasía") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editPhone,
                        onValueChange = { editPhone = it },
                        label = { Text("Teléfono de Contacto") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editAddress,
                        onValueChange = { editAddress = it },
                        label = { Text("Dirección de Despacho") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = user.copy(
                            name = editName.trim(),
                            businessName = editBizName.trim(),
                            phone = editPhone.trim(),
                            address = editAddress.trim()
                        )
                        onUpdateUser(updated)
                        showEditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MartiniGreen)
                ) {
                    Text("Guardar Cambios")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Modal Confirmar Cierre de Sesión
    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = { Text("¿Cerrar Sesión?", fontWeight = FontWeight.Bold) },
            text = { Text("¿Estás seguro que deseas salir de tu cuenta en Club Master Martini?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutConfirm = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
                ) {
                    Text("Sí, Salir")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun MetricCard(
    modifier: Modifier = Modifier,
    number: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = BakerySurface,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = number, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(text = label, fontSize = 12.sp, color = TextSecondary, maxLines = 1)
        }
    }
}

@Composable
private fun ProfileInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MartiniGreenMid, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = title, fontSize = 12.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            Text(text = if (value.isBlank()) "No especificado" else value, fontSize = 14.sp, color = TextPrimary, fontWeight = FontWeight.SemiBold)
        }
    }
}
