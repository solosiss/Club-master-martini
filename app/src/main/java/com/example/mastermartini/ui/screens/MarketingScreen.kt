package com.example.mastermartini.ui.screens

import com.example.mastermartini.ui.components.Reveal
import com.example.mastermartini.ui.components.rememberAnimationsEnabled
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mastermartini.data.model.Lesson
import com.example.mastermartini.data.repository.MarketingDemo
import com.example.mastermartini.ui.components.LessonThumbnail
import com.example.mastermartini.ui.theme.BakeryWarmBackground
import com.example.mastermartini.ui.theme.ChartGreenLight
import com.example.mastermartini.ui.theme.ChartGreenMid
import com.example.mastermartini.ui.theme.GoldInk
import com.example.mastermartini.ui.theme.GoldSurface
import com.example.mastermartini.ui.theme.MartiniGreen
import com.example.mastermartini.ui.theme.TextPrimary
import com.example.mastermartini.ui.theme.TextSecondary
import java.util.Locale

private fun Int.fmt(): String = String.format(Locale("es", "CL"), "%,d", this)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketingScreen(lessons: List<Lesson>, onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vista Marketing", color = Color.White, style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack, modifier = Modifier.size(56.dp)) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MartiniGreen)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(BakeryWarmBackground),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            item { DemoNotice() }
            item { LeadsByRubro() }
            item { SamplesByStatus() }
            item { TopViewed(lessons) }
        }
    }
}

@Composable
private fun DemoNotice() {
    Surface(shape = RoundedCornerShape(12.dp), color = GoldSurface, modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Info, contentDescription = null, tint = GoldInk, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(10.dp))
            Text(
                "Datos de demostración. Las cifras son de ejemplo y no corresponden a clientes reales.",
                style = MaterialTheme.typography.bodyLarge,
                color = GoldInk
            )
        }
    }
}

@Composable
private fun LeadsByRubro() {
    val max = MarketingDemo.leadsByRubro.maxOf { it.second }.toFloat()
    // Todas las barras crecen a la vez, con los números y el total: ninguna cifra llega a destiempo
    val animate = rememberAnimationsEnabled()
    val grow = remember { Animatable(if (animate) 0f else 1f) }
    LaunchedEffect(Unit) { if (animate) grow.animateTo(1f, tween(1000, easing = FastOutSlowInEasing)) }
    val liveTotal = (MarketingDemo.totalLeads * grow.value).toInt()
    val description = "Leads por rubro: " + MarketingDemo.leadsByRubro.joinToString(", ") { "${it.first.displayName} ${it.second}" }
    Column(Modifier.semantics { contentDescription = description }) {
        Text("Leads por rubro", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
        Row(verticalAlignment = Alignment.Bottom) {
            Text(liveTotal.fmt(), style = MaterialTheme.typography.displaySmall, color = MartiniGreen)
            Spacer(Modifier.width(8.dp))
            Text("en total", style = MaterialTheme.typography.bodyLarge, color = TextSecondary, modifier = Modifier.padding(bottom = 6.dp))
        }
        Spacer(Modifier.height(16.dp))
        MarketingDemo.leadsByRubro.forEach { (type, value) ->
            Text(type.displayName, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .weight(1f)
                        .height(16.dp)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth(value / max * grow.value)
                            .height(16.dp)
                            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                            .background(MartiniGreen)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text((value * grow.value).toInt().fmt(), style = MaterialTheme.typography.titleMedium, color = TextPrimary, modifier = Modifier.width(48.dp), textAlign = TextAlign.End)
            }
            Spacer(Modifier.height(14.dp))
        }
    }
}

@Composable
private fun SamplesByStatus() {
    val ramp = listOf(ChartGreenLight, ChartGreenMid, MartiniGreen)
    val total = MarketingDemo.totalSamples.toFloat()
    // El anillo se llena y los números suben a la vez cuando la sección aparece; el resto es fijo
    val animate = rememberAnimationsEnabled()
    val progress = remember { Animatable(if (animate) 0f else 1f) }
    LaunchedEffect(Unit) { if (animate) progress.animateTo(1f, tween(1000, easing = FastOutSlowInEasing)) }
    val description = "Muestras solicitadas: " + MarketingDemo.samplesByStatus.joinToString(", ") { "${it.first} ${it.second}" }
    Column(Modifier.semantics { contentDescription = description }) {
        Text("Muestras solicitadas", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(140.dp), contentAlignment = Alignment.Center) {
                Canvas(Modifier.fillMaxSize()) {
                    val stroke = 22.dp.toPx()
                    val inset = stroke / 2
                    val gap = 3f // grados de separación entre segmentos
                    var start = -90f
                    var offset = 0f
                    MarketingDemo.samplesByStatus.forEachIndexed { i, (_, v) ->
                        val sweep = v / total * 360f
                        val shown = (progress.value * 360f - offset).coerceIn(0f, sweep)
                        val drawn = if (shown >= sweep) sweep - gap else (shown - gap / 2).coerceAtLeast(0f)
                        offset += sweep
                        drawArc(
                            color = ramp[i],
                            startAngle = start + gap / 2,
                            sweepAngle = drawn,
                            useCenter = false,
                            topLeft = Offset(inset, inset),
                            size = Size(size.width - stroke, size.height - stroke),
                            style = Stroke(width = stroke, cap = StrokeCap.Butt)
                        )
                        start += sweep
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(MarketingDemo.totalSamples.fmt(), style = MaterialTheme.typography.headlineSmall, color = TextPrimary)
                    Text("muestras", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
            }
            Spacer(Modifier.width(20.dp))
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                MarketingDemo.samplesByStatus.forEachIndexed { i, (label, value) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(14.dp).clip(RoundedCornerShape(4.dp)).background(ramp[i]))
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(label, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                            Text("${(value * progress.value).toInt().fmt()} (${(value / total * 100 * progress.value).toInt()}%)", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopViewed(lessons: List<Lesson>) {
    Column {
        Text("Videos más vistos", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
        Spacer(Modifier.height(12.dp))
        MarketingDemo.topViewed.forEachIndexed { i, (id, views) ->
            val lesson = lessons.firstOrNull { it.id == id } ?: return@forEachIndexed
            Reveal(index = i, modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                LessonThumbnail(lesson, Modifier.size(width = 112.dp, height = 63.dp).clip(RoundedCornerShape(8.dp)))
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(lesson.title, style = MaterialTheme.typography.bodyLarge, color = TextPrimary, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Text("${views.fmt()} vistas", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                }
            }
            }
        }
    }
}
