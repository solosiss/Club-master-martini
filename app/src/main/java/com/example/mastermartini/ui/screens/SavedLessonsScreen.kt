package com.example.mastermartini.ui.screens

import androidx.compose.ui.Alignment
import com.example.mastermartini.ui.components.StatusBarScrim
import com.example.mastermartini.ui.components.BrandHeader
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.Icons
import com.example.mastermartini.ui.components.EmptyState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermartini.data.model.Lesson
import com.example.mastermartini.data.model.User
import com.example.mastermartini.ui.theme.*
import com.example.mastermartini.ui.theme.MartiniGreen

@Composable
fun SavedLessonsScreen(
    user: User,
    allLessons: List<Lesson>,
    onSelectLesson: (Lesson) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val savedLessons = allLessons.filter { user.favoriteLessonIds.contains(it.id) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BakeryWarmBackground)
        ) {
            item {
                BrandHeader(
                    title = "Mis clases guardadas",
                    onBack = onNavigateBack
                )
            }

            if (savedLessons.isEmpty()) {
                item {
                    EmptyState(
                        icon = Icons.Default.BookmarkBorder,
                        title = "No tienes clases guardadas",
                        message = "Toca el corazón en cualquier lección para tenerla siempre a mano en tu taller."
                    )
                }
            } else {
                items(savedLessons) { lesson ->
                    val isCompleted = user.completedLessonIds.contains(lesson.id)
                    LessonCard(
                        lesson = lesson,
                        isFavorite = true,
                        isCompleted = isCompleted,
                        onCardClick = { onSelectLesson(lesson) },
                        onToggleFavorite = { onToggleFavorite(lesson.id) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
        StatusBarScrim(Modifier.align(Alignment.TopStart))
    }
}
