package com.example.mastermartini.ui.screens

import com.example.mastermartini.ui.components.rememberAnimationsEnabled
import com.example.mastermartini.ui.components.Reveal
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Animatable
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import android.util.Patterns
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mastermartini.R
import com.example.mastermartini.ui.components.BrandLogo
import com.example.mastermartini.ui.theme.ErrorRed
import com.example.mastermartini.ui.theme.ErrorSurface
import com.example.mastermartini.ui.theme.HeroBackdrop
import com.example.mastermartini.ui.theme.MartiniGold
import com.example.mastermartini.ui.theme.MartiniGreen
import com.example.mastermartini.ui.theme.MartiniGreenDark
import com.example.mastermartini.ui.theme.MartiniGreenPale
import com.example.mastermartini.ui.theme.TextPrimary
import com.example.mastermartini.ui.theme.TextSecondary

private val FieldShape = RoundedCornerShape(12.dp)

// Cuentas de demostración para la reunión comercial (datos ficticios)
private data class DemoAccount(@DrawableRes val iconRes: Int, val label: String, val email: String)

private val DemoAccounts = listOf(
    DemoAccount(R.drawable.ic_biz_cake, "Pastelería", "pasteleria@mastermartini.cl"),
    DemoAccount(R.drawable.ic_biz_chocolate, "Chocolatería", "chocolateria@mastermartini.cl"),
    DemoAccount(R.drawable.ic_biz_bread, "Panadería", "panaderia@mastermartini.cl")
)

@Composable
fun LoginScreen(
    onLoginSuccess: (email: String, pass: String) -> Unit,
    onNavigateToRegister: () -> Unit,
    errorMessage: String? = null
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MartiniGreen)
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        // Hero: fondo oscuro liso arriba con el logo, la foto del producto emerge debajo y se funde con el verde
        // La foto se acerca muy despacio (5%) mientras se ve la pantalla
        val zoom = remember { Animatable(1f) }
        val animate = rememberAnimationsEnabled()
        LaunchedEffect(Unit) { if (animate) zoom.animateTo(1.05f, tween(12000, easing = LinearEasing)) }

        Box(modifier = Modifier.fillMaxWidth().height(420.dp).background(HeroBackdrop).clipToBounds()) {
            Image(
                painter = painterResource(R.drawable.login_hero),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(330.dp)
                    .graphicsLayer { scaleX = zoom.value; scaleY = zoom.value }
            )
            Box(
                Modifier.matchParentSize().background(
                    Brush.verticalGradient(
                        0f to HeroBackdrop,
                        0.42f to HeroBackdrop,
                        0.7f to HeroBackdrop.copy(alpha = 0f),
                        0.84f to Color.Transparent,
                        1f to MartiniGreen
                    )
                )
            )
            BrandLogo(
                width = 150.dp,
                modifier = Modifier.align(Alignment.TopStart).statusBarsPadding().padding(start = 24.dp, top = 16.dp)
            )
            // Titular editorial apoyado en la base de la foto
            Column(modifier = Modifier.align(Alignment.BottomStart).padding(start = 24.dp, end = 24.dp, bottom = 20.dp)) {
                Reveal(0) {
                    Text(
                        "BIENVENIDO AL",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 3.sp,
                        color = MartiniGold
                    )
                }
                Reveal(1) {
                    Text(
                        "Club Master Martini",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.3.sp,
                        color = Color.White
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))

            val displayError = localError ?: errorMessage
            if (displayError != null) {
                Surface(
                    color = ErrorSurface,
                    shape = FieldShape,
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }.fillMaxWidth()
                ) {
                    Text(
                        displayError,
                        color = ErrorRed,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            FieldLabel("Correo electrónico")
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; localError = null },
                singleLine = true,
                placeholder = { Text("ejemplo@negocio.cl", color = TextSecondary) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = FieldShape,
                colors = fieldColors(),
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
            )
            Spacer(Modifier.height(16.dp))

            FieldLabel("Contraseña")
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; localError = null },
                singleLine = true,
                placeholder = { Text("Mínimo 6 caracteres", color = TextSecondary) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                shape = FieldShape,
                colors = fieldColors(),
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
            )
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    localError = when {
                        email.isBlank() -> "Ingresa tu correo electrónico."
                        !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> "Ingresa un correo válido, por ejemplo nombre@negocio.cl."
                        password.length < 6 -> "La contraseña debe tener al menos 6 caracteres."
                        else -> null
                    }
                    if (localError == null) onLoginSuccess(email, password)
                },
                shape = FieldShape,
                colors = ButtonDefaults.buttonColors(containerColor = MartiniGold, contentColor = MartiniGreenDark),
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
            ) {
                Text("Ingresar", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold)
            }

            Spacer(Modifier.height(28.dp))
            Text(
                "Acceso rápido por rubro (cuentas de demostración)",
                style = MaterialTheme.typography.bodyLarge,
                color = MartiniGreenPale
            )
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DemoAccounts.forEachIndexed { i, account ->
                    Reveal(index = 3 + i, modifier = Modifier.weight(1f)) {
                        DemoTile(account.label, account.iconRes, Modifier.fillMaxWidth()) {
                            email = account.email
                            password = "123456"
                            localError = null
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            TextButton(onClick = onNavigateToRegister, modifier = Modifier.heightIn(min = 56.dp)) {
                Text("¿Aún no tienes cuenta? Regístrate", color = Color.White, style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
    )
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = MartiniGold,
    unfocusedBorderColor = Color.Transparent,
    focusedTextColor = TextPrimary,
    unfocusedTextColor = TextPrimary
)

@Composable
private fun DemoTile(label: String, @DrawableRes iconRes: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier.heightIn(min = 80.dp).clickable(role = Role.Button) { onClick() },
        shape = FieldShape,
        color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(painterResource(iconRes), contentDescription = null, tint = MartiniGreen, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(4.dp))
            Text(label, style = MaterialTheme.typography.labelLarge, color = MartiniGreenDark, textAlign = TextAlign.Center)
        }
    }
}
