package com.example.mastermartini.ui.screens

import com.example.mastermartini.ui.components.StatusBarScrim
import com.example.mastermartini.ui.components.BrandHeader
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.mastermartini.ui.components.categoryIcon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermartini.data.model.Product
import com.example.mastermartini.data.model.User
import com.example.mastermartini.ui.theme.*
import com.example.mastermartini.ui.theme.BakerySurface
import com.example.mastermartini.ui.theme.MartiniGreenDark
import com.example.mastermartini.ui.theme.MartiniGreen
import com.example.mastermartini.ui.theme.MartiniGold
import com.example.mastermartini.ui.theme.TextPrimary
import com.example.mastermartini.ui.theme.TextSecondary

@Composable
fun ProductCatalogScreen(
    user: User,
    products: List<Product>,
    onRequestSample: (Product, notes: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }
    var sampleProductDialog by remember { mutableStateOf<Product?>(null) }
    var sampleSuccessMessage by remember { mutableStateOf<String?>(null) }
    var pdfDialogProduct by remember { mutableStateOf<Product?>(null) }

    val categories = listOf("Todos", "Chocolatería", "Cremas Vegetales", "Masas y Bollería", "Rellenos y Pastas", "Heladería")

    val filteredProducts = products.filter { p ->
        val matchesCategory = (selectedCategory == "Todos") || p.category.equals(selectedCategory, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() ||
                p.name.contains(searchQuery, ignoreCase = true) ||
                p.line.contains(searchQuery, ignoreCase = true) ||
                p.description.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BakeryWarmBackground)
        ) {
            item {
                BrandHeader(
                    title = "Catálogo técnico de insumos",
                    onBack = onNavigateBack
                )
            }

            // Búsqueda
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar insumos (Caravella, Decor Up, FiorFiore...)", fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MartiniGreen) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
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

            // Chips categorías
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) MartiniGreen else Color.White,
                            shadowElevation = if (isSelected) 3.dp else 1.dp,
                            modifier = Modifier.clickable(role = Role.Button) { selectedCategory = cat }
                        ) {
                            Text(
                                text = cat,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else TextPrimary,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(filteredProducts) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = BakerySurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painterResource(categoryIcon(product.category)), contentDescription = null, tint = MartiniGreen, modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = product.line,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MartiniGreen
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = GoldSurface
                                    ) {
                                        Text(
                                            text = product.badge,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = GoldInk,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = product.name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )

                                Text(
                                    text = "Formato: ${product.format} · SKU: ${product.sku}",
                                    fontSize = 12.sp,
                                    color = TextSecondary
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

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { pdfDialogProduct = product },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MartiniGreen)
                            ) {
                                Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Ficha PDF", fontSize = 12.sp)
                            }

                            Button(
                                onClick = { sampleProductDialog = product },
                                modifier = Modifier.weight(1.3f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MartiniGold,
                                    contentColor = MartiniGreenDark
                                )
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Pedir Muestra", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
        StatusBarScrim(Modifier.align(Alignment.TopStart))
    }

    // Modal de Muestra
    sampleProductDialog?.let { product ->
        var notes by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { sampleProductDialog = null },
            title = { Text("Solicitar Muestra Oficial", fontWeight = FontWeight.Bold, color = MartiniGreen) },
            text = {
                Column {
                    Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = "Formato: ${product.format}", fontSize = 12.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Despacho a: ${user.businessName} (${user.address})", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = { Text("Notas para el asesor técnico…", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onRequestSample(product, notes)
                        sampleProductDialog = null
                        sampleSuccessMessage = "Solicitud enviada a Master Martini Chile para ${product.name}."
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MartiniGreen)
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { sampleProductDialog = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Modal PDF
    pdfDialogProduct?.let { prod ->
        AlertDialog(
            onDismissRequest = { pdfDialogProduct = null },
            title = { Text("Ficha Técnica Oficial") },
            text = {
                Text("Descargando especificación técnica oficial y tabla nutricional de ${prod.name} (Origen: Unigrà S.p.A. Conselice, Italia).")
            },
            confirmButton = {
                Button(onClick = { pdfDialogProduct = null }) {
                    Text("Cerrar")
                }
            }
        )
    }

    sampleSuccessMessage?.let { msg ->
        AlertDialog(
            onDismissRequest = { sampleSuccessMessage = null },
            title = { Text("Muestra Registrada", color = SuccessGreen, fontWeight = FontWeight.Bold) },
            text = { Text(msg) },
            confirmButton = {
                Button(onClick = { sampleSuccessMessage = null }) {
                    Text("OK")
                }
            }
        )
    }
}
