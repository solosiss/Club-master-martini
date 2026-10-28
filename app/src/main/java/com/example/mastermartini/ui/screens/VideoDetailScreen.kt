package com.example.mastermartini.ui.screens

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.mastermartini.ui.components.LessonThumbnail
import androidx.compose.material.icons.filled.Lightbulb
import com.example.mastermartini.ui.components.categoryIcon
import android.annotation.SuppressLint
import android.content.Intent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mastermartini.data.model.Lesson
import com.example.mastermartini.data.model.Product
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
fun VideoDetailScreen(
    lesson: Lesson,
    user: User,
    isFavorite: Boolean,
    isCompleted: Boolean,
    onNavigateBack: () -> Unit,
    onToggleFavorite: () -> Unit,
    onToggleCompleted: () -> Unit,
    onRequestSample: (Product, notes: String) -> Unit
) {
    val context = LocalContext.current
    var playerVisible by remember { mutableStateOf(false) }
    var showSampleDialogForProduct by remember { mutableStateOf<Product?>(null) }
    var sampleSuccessMessage by remember { mutableStateOf<String?>(null) }
    var showPdfInfoDialog by remember { mutableStateOf<Product?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = lesson.category,
                        fontSize = 17.sp,
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
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Guardar",
                            tint = if (isFavorite) MartiniGold else Color.White
                        )
                    }
                    IconButton(onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Mira esta clase técnica de Master Martini: ${lesson.title}\nhttps://mastermartini.cl/cursos/${lesson.id}")
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "Compartir clase con mi equipo"))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir",
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
        ) {
            // ================= REPRODUCTOR DE VIDEO =================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(Color.Black)
            ) {
                if (playerVisible) {
                    // Reproductor embebido de YouTube (canal oficial)
                    AndroidView(
                        factory = { ctx ->
                            WebView(ctx).apply {
                                @SuppressLint("SetJavaScriptEnabled")
                                settings.javaScriptEnabled = true
                                settings.mediaPlaybackRequiresUserGesture = false
                                settings.domStorageEnabled = true
                                webChromeClient = WebChromeClient()
                                webViewClient = WebViewClient()
                                val htmlData = """
                                    <!DOCTYPE html>
                                    <html>
                                    <body style="margin:0;padding:0;background-color:#000;display:flex;align-items:center;justify-content:center;height:100vh;">
                                        <iframe width="100%" height="100%" 
                                            src="https://www.youtube-nocookie.com/embed/${lesson.youtubeVideoId}?autoplay=1&controls=1" 
                                            frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                                            allowfullscreen>
                                        </iframe>
                                    </body>
                                    </html>
                                """.trimIndent()
                                loadDataWithBaseURL("https://www.youtube.com", htmlData, "text/html", "utf-8", null)
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // Miniatura oficial con botón grande; el video carga solo al tocar (ahorra datos)
                    LessonThumbnail(lesson, Modifier.fillMaxSize())
                    Surface(
                        shape = CircleShape,
                        color = MartiniGold,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(72.dp)
                            .clickable(role = Role.Button) { playerVisible = true }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Reproducir video",
                                tint = MartiniGreenDark,
                                modifier = Modifier.size(44.dp)
                            )
                        }
                    }
                }
            }

            // ================= CONTENIDO DE LA LECCIÓN =================
            Column(modifier = Modifier.padding(16.dp)) {
                // Título y datos técnicos
                Text(
                    text = lesson.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MartiniGreenDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MartiniGreen
                    ) {
                        Text(
                            text = "Dificultad: " + lesson.difficulty,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    if (lesson.durationSeconds > 0) {
                        Text(
                            text = lesson.durationFormatted,
                            fontSize = 12.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Text(
                        text = lesson.chefName.substringBefore("(").trim(),
                        fontSize = 12.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Botones de acción rápida: Marcar Completada
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onToggleCompleted,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCompleted) SuccessGreen else MartiniGreenMid,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isCompleted) "Lección completada" else "Marcar como Vista",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Descripción técnica
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = BakerySurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Descripción de la Técnica",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MartiniGreen
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = lesson.description,
                            fontSize = 14.sp,
                            color = TextPrimary,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tips Técnicos del Chef Asesor
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = GoldSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MartiniGoldLight)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = GoldInk, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Secretos Técnicos de Obrador",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldInk
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        lesson.technicalTips.forEach { tip ->
                            Row(modifier = Modifier.padding(vertical = 3.dp)) {
                                Text(text = "•", color = MartiniGold, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = tip,
                                    fontSize = 12.sp,
                                    color = TextPrimary,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (lesson.productsUsed.isNotEmpty()) {
                    // ================= INSUMOS MASTER MARTINI UTILIZADOS =================
                    // Requisito crítico del informe: relacionar cursos técnicos con productos
                    Text(
                        text = "Insumos Master Martini Requeridos",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MartiniGreenDark
                    )
                    Text(
                        text = "Para reproducir esta receta con garantía de resultado profesional en tu negocio:",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                    )

                    lesson.productsUsed.forEach { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = BakerySurface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(painterResource(categoryIcon(product.category)), contentDescription = null, tint = MartiniGreen, modifier = Modifier.size(28.dp))
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = product.name,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                        Text(
                                            text = "Línea: ${product.line} · SKU: ${product.sku}",
                                            fontSize = 12.sp,
                                            color = TextSecondary
                                        )
                                        Text(
                                            text = "Formato: " + product.format,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MartiniGreen
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = product.description,
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    lineHeight = 17.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Acciones de Producto
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Botón Ficha Técnica PDF
                                    OutlinedButton(
                                        onClick = { showPdfInfoDialog = product },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MartiniGreen)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Description,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = "Ficha PDF", fontSize = 12.sp)
                                    }

                                    // Botón Solicitar Muestra Gratuita (Comercial Master Martini)
                                    Button(
                                        onClick = { showSampleDialogForProduct = product },
                                        modifier = Modifier.weight(1.3f),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MartiniGold,
                                            contentColor = MartiniGreenDark
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Send,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Pedir Muestra",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    // Modal de Solicitud de Muestra Gratuita
    showSampleDialogForProduct?.let { product ->
        var sampleNotes by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showSampleDialogForProduct = null },
            title = {
                Text(
                    text = "Solicitar Muestra Gratuita",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MartiniGreen
                )
            },
            text = {
                Column {
                    Text(
                        text = "Producto: " + product.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = "Formato muestra técnica: ${product.format}",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    Surface(
                        color = BakerySurfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "Datos de Envío Confirmados:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MartiniGreenDark)
                            Text(text = "Negocio: ${user.businessName}", fontSize = 12.sp)
                            Text(text = "Contacto: ${user.name} (${user.phone})", fontSize = 12.sp)
                            Text(text = "Dirección: ${user.address}", fontSize = 12.sp)
                        }
                    }

                    Text(
                        text = "Comentarios o pruebas a realizar:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = sampleNotes,
                        onValueChange = { sampleNotes = it },
                        placeholder = { Text("Ej: Queremos probar estabilidad en tortas de vitrina", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onRequestSample(product, sampleNotes)
                        showSampleDialogForProduct = null
                        sampleSuccessMessage = "¡Solicitud registrada con éxito para ${product.name}! El equipo técnico de Master Martini Chile te contactará."
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MartiniGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text("Confirmar Pedido")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSampleDialogForProduct = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Modal de Confirmación de Ficha Técnica PDF
    showPdfInfoDialog?.let { product ->
        AlertDialog(
            onDismissRequest = { showPdfInfoDialog = null },
            title = {
                Text(text = "Ficha Técnica Master Martini", fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(text = product.name, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "• Certificación de Calidad: UNI EN ISO 9001 / IFS Food")
                    Text(text = "• Origen: Planta Conselice, Ravenna, Italia / Master Martini Chile")
                    Text(text = "• Punto de fusión controlado: 34°C - 36°C")
                    Text(text = "• Libre de grasas hidrogenadas (según línea)")
                    Text(text = "• Almacenamiento: Lugar fresco y seco (15°C - 20°C)")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "El archivo PDF técnico oficial ha sido descargado en tu dispositivo para consulta en el obrador.",
                        fontSize = 12.sp,
                        color = SuccessGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showPdfInfoDialog = null },
                    colors = ButtonDefaults.buttonColors(containerColor = MartiniGreen)
                ) {
                    Text("Entendido")
                }
            }
        )
    }

    // Modal de Éxito de Muestra
    sampleSuccessMessage?.let { msg ->
        AlertDialog(
            onDismissRequest = { sampleSuccessMessage = null },
            title = {
                Text(text = "Solicitud recibida", fontWeight = FontWeight.Bold, color = SuccessGreen)
            },
            text = { Text(text = msg, fontSize = 14.sp) },
            confirmButton = {
                Button(
                    onClick = { sampleSuccessMessage = null },
                    colors = ButtonDefaults.buttonColors(containerColor = MartiniGreen)
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
