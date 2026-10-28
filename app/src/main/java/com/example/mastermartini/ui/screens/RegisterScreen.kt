package com.example.mastermartini.ui.screens

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.Role
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermartini.data.model.BusinessSize
import com.example.mastermartini.data.model.BusinessType
import com.example.mastermartini.data.model.User
import com.example.mastermartini.ui.theme.*
import com.example.mastermartini.ui.theme.BakerySurface
import com.example.mastermartini.ui.theme.MartiniGreenDark
import com.example.mastermartini.ui.theme.MartiniGreen
import com.example.mastermartini.ui.theme.MartiniGold
import com.example.mastermartini.ui.theme.TextPrimary
import com.example.mastermartini.ui.theme.TextSecondary
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: (user: User, pass: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    // Campos del formulario
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var businessName by remember { mutableStateOf("") }
    var businessRut by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedBusinessType by remember { mutableStateOf(BusinessType.PANADERIA_PASTELERIA) }
    var selectedBusinessSize by remember { mutableStateOf(BusinessSize.ARTESANAL) }
    var largeTextMode by remember { mutableStateOf(false) }

    // Intereses técnicos
    val availableInterests = listOf(
        "Chocolatería y Bombonería",
        "Cremas Vegetales y Decoración",
        "Masas de Hoja y Bollería",
        "Rellenos Horno-Resistentes",
        "Helado Artesanal y Bases",
        "Glaseados de Espejo"
    )
    val selectedInterests = remember { mutableStateOf(setOf("Cremas Vegetales y Decoración", "Masas de Hoja y Bollería")) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Registro Club Master Martini",
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
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Intro
            Text(
                text = "Crea tu Cuenta Profesional",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MartiniGreenDark
            )
            Text(
                text = "Accede a capacitaciones gratuitas de chefs internacionales y recibe muestras de insumos.",
                fontSize = 14.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Banner de error si hay
            if (errorMessage != null) {
                Surface(
                    color = ErrorSurface,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .semantics { liveRegion = LiveRegionMode.Polite }
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = errorMessage ?: "",
                        color = ErrorRed,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            // Sección 1: Datos Personales
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BakerySurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(number = "1", title = "Datos de Contacto")

                    CustomInputField(
                        label = "Nombre Completo *",
                        value = name,
                        onValueChange = { name = it; errorMessage = null },
                        placeholder = "Ej: Carlos Henríquez",
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomInputField(
                        label = "Correo Electrónico *",
                        value = email,
                        onValueChange = { email = it; errorMessage = null },
                        placeholder = "contacto@mipanaderia.cl",
                        leadingIcon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomInputField(
                        label = "Teléfono de Contacto *",
                        value = phone,
                        onValueChange = { phone = it; errorMessage = null },
                        placeholder = "+56 9 1234 5678",
                        leadingIcon = Icons.Default.Phone,
                        keyboardType = KeyboardType.Phone
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomInputField(
                        label = "Contraseña de Acceso *",
                        value = password,
                        onValueChange = { password = it; errorMessage = null },
                        placeholder = "Mínimo 6 caracteres",
                        leadingIcon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                        isPassword = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección 2: Información Comercial (Requerimiento Caso DSY1105)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BakerySurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(number = "2", title = "Información del Negocio")

                    CustomInputField(
                        label = "Nombre del Negocio / Taller *",
                        value = businessName,
                        onValueChange = { businessName = it; errorMessage = null },
                        placeholder = "Ej: Pastelería Dulce Arte",
                        leadingIcon = Icons.Default.Business
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomInputField(
                        label = "RUT de Empresa o Emprendedor *",
                        value = businessRut,
                        onValueChange = { businessRut = it; errorMessage = null },
                        placeholder = "Ej: 76.543.210-K",
                        leadingIcon = Icons.Default.Business
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomInputField(
                        label = "Dirección Comercial / Despacho Muestras *",
                        value = address,
                        onValueChange = { address = it; errorMessage = null },
                        placeholder = "Calle, Número, Comuna, Ciudad",
                        leadingIcon = Icons.Default.Business
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección 3: Segmentación por Tipo de Negocio (Requisito Clave)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BakerySurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(number = "3", title = "Tipo de Negocio")
                    Text(
                        text = "Selecciona el rubro principal para personalizar los cursos y productos recomendados:",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    BusinessType.entries.forEach { type ->
                        val isSelected = selectedBusinessType == type
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) MartiniGreen else OutlineNeutral,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable(role = Role.Button) { selectedBusinessType = type },
                            color = if (isSelected) MartiniGreenTint else BakerySurface
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(painterResource(type.iconRes), contentDescription = null, tint = MartiniGreen, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = type.displayName,
                                        fontSize = 15.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) MartiniGreen else TextPrimary
                                    )
                                    Text(
                                        text = type.description,
                                        fontSize = 12.sp,
                                        color = TextSecondary
                                    )
                                }
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MartiniGreen,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección 4: Tamaño del Negocio
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BakerySurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(number = "4", title = "Tamaño del Negocio")

                    BusinessSize.entries.forEach { size ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(role = Role.Button) { selectedBusinessSize = size }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedBusinessSize == size,
                                onClick = { selectedBusinessSize = size },
                                colors = RadioButtonDefaults.colors(selectedColor = MartiniGreen)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = size.displayName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Equipo: " + size.range,
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección 5: Encuesta de Intereses Técnicos
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BakerySurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(number = "5", title = "Encuesta de Intereses Técnicos")
                    Text(
                        text = "Elige las áreas técnicas sobre las que te interesa recibir videos y recetas:",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        availableInterests.forEach { interest ->
                            val isSelected = selectedInterests.value.contains(interest)
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) MartiniGreen else BakerySurfaceVariant,
                                modifier = Modifier.clickable(role = Role.Button) {
                                    val current = selectedInterests.value.toMutableSet()
                                    if (isSelected) current.remove(interest) else current.add(interest)
                                    selectedInterests.value = current
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp).padding(end = 4.dp)
                                        )
                                    }
                                    Text(
                                        text = interest,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isSelected) Color.White else TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Opción de Accesibilidad (Mayores de 50 años / Taller de cocina)
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
                            text = "Modo Lectura Grande (Senior / Taller)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MartiniGreenDark
                        )
                        Text(
                            text = "Aumenta el tamaño tipográfico para facilitar la lectura en obrador o para usuarios mayores de 50 años.",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                    Switch(
                        checked = largeTextMode,
                        onCheckedChange = { largeTextMode = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = MartiniGold, checkedTrackColor = MartiniGreen)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de Envío / Registrarse
            Button(
                onClick = {
                    if (name.isBlank()) {
                        errorMessage = "Por favor ingresa tu nombre completo."
                    } else if (email.isBlank() || !email.contains("@")) {
                        errorMessage = "Ingresa un correo electrónico válido."
                    } else if (phone.isBlank()) {
                        errorMessage = "Ingresa un teléfono de contacto."
                    } else if (password.length < 6) {
                        errorMessage = "La contraseña debe tener al menos 6 caracteres."
                    } else if (businessName.isBlank()) {
                        errorMessage = "Por favor indica el nombre de tu negocio o taller."
                    } else if (address.isBlank()) {
                        errorMessage = "Por favor indica tu dirección de despacho o taller."
                    } else {
                        val newUser = User(
                            id = "usr_" + UUID.randomUUID().toString().take(8),
                            name = name.trim(),
                            email = email.trim().lowercase(),
                            phone = phone.trim(),
                            businessName = businessName.trim(),
                            businessRut = if (businessRut.isBlank()) "76.999.000-1" else businessRut.trim(),
                            address = address.trim(),
                            businessType = selectedBusinessType,
                            businessSize = selectedBusinessSize,
                            interests = selectedInterests.value.toList(),
                            largeTextEnabled = largeTextMode
                        )
                        onRegisterSuccess(newUser, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MartiniGreen,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "CREAR MI CUENTA PROFESIONAL",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionHeader(number: String, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = MartiniGold,
            modifier = Modifier.size(24.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = number,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MartiniGreenDark
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MartiniGreen
        )
    }
}

@Composable
private fun CustomInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = TextSecondary, fontSize = 14.sp) },
            leadingIcon = {
                Icon(leadingIcon, contentDescription = null, tint = MartiniGreen)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MartiniGreen,
                unfocusedBorderColor = OutlineNeutral
            )
        )
    }
}
