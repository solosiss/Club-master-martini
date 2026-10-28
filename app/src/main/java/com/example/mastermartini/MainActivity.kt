package com.example.mastermartini

import com.example.mastermartini.ui.components.rememberAnimationsEnabled
import androidx.compose.animation.core.tween
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.mastermartini.data.model.Lesson
import com.example.mastermartini.data.repository.MasterMartiniRepository
import com.example.mastermartini.ui.screens.HomeScreen
import com.example.mastermartini.ui.screens.LoginScreen
import com.example.mastermartini.ui.screens.MarketingScreen
import com.example.mastermartini.ui.screens.SplashScreen
import com.example.mastermartini.ui.screens.ProductCatalogScreen
import com.example.mastermartini.ui.screens.ProfileScreen
import com.example.mastermartini.ui.screens.RegisterScreen
import com.example.mastermartini.ui.screens.SavedLessonsScreen
import com.example.mastermartini.ui.screens.VideoDetailScreen
import com.example.mastermartini.ui.theme.MastermartiniTheme

sealed class AppScreen {
    object Splash : AppScreen()
    object Login : AppScreen()
    object Register : AppScreen()
    object Main : AppScreen()
    data class VideoDetail(val lesson: Lesson) : AppScreen()
    object Profile : AppScreen()
    object Marketing : AppScreen()
}

class MainActivity : ComponentActivity() {

    private lateinit var repository: MasterMartiniRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        repository = MasterMartiniRepository.shared

        setContent {
            ClubMasterMartiniApp(repository = repository)
        }
    }
}

@Composable
fun ClubMasterMartiniApp(repository: MasterMartiniRepository) {
    var currentUser by remember { mutableStateOf(repository.getCurrentUser()) }
    var currentScreen by remember {
        mutableStateOf<AppScreen>(
            AppScreen.Splash
        )
    }
    var currentMainTab by remember { mutableIntStateOf(0) }
    var loginError by remember { mutableStateOf<String?>(null) }
    var sampleRequestsState by remember { mutableStateOf(repository.getSampleRequests()) }

    // Manejador del botón Atrás del sistema Android
    BackHandler(enabled = (currentScreen is AppScreen.Main && currentMainTab != 0) || (currentScreen !is AppScreen.Login && currentScreen !is AppScreen.Main && currentScreen !is AppScreen.Splash)) {
        when (currentScreen) {
            is AppScreen.Main -> currentMainTab = 0
            is AppScreen.VideoDetail -> currentScreen = AppScreen.Main
            is AppScreen.Profile -> currentScreen = AppScreen.Main
            is AppScreen.Marketing -> currentScreen = AppScreen.Profile
            is AppScreen.Register -> currentScreen = AppScreen.Login
            else -> {}
        }
    }

    MastermartiniTheme(largeText = currentUser?.largeTextEnabled == true) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
    // Fundido de 130 ms entre pantallas (0 si el teléfono tiene las animaciones desactivadas)
    val animate = rememberAnimationsEnabled()
    Crossfade(
        targetState = currentScreen,
        animationSpec = tween(if (animate) 130 else 0),
        label = "pantalla"
    ) { screen ->
    when (screen) {
        is AppScreen.Splash -> {
            SplashScreen(onFinished = {
                currentScreen = if (currentUser != null) AppScreen.Main else AppScreen.Login
            })
        }

        is AppScreen.Login -> {
            LoginScreen(
                onLoginSuccess = { email, pass ->
                    val result = repository.login(email, pass)
                    if (result.isSuccess) {
                        currentUser = result.getOrNull()
                        sampleRequestsState = repository.getSampleRequests()
                        loginError = null
                        currentScreen = AppScreen.Main
                    } else {
                        loginError = result.exceptionOrNull()?.message ?: "Error al iniciar sesión"
                    }
                },
                onNavigateToRegister = {
                    loginError = null
                    currentScreen = AppScreen.Register
                },
                errorMessage = loginError
            )
        }

        is AppScreen.Register -> {
            RegisterScreen(
                onRegisterSuccess = { newUser, pass ->
                    val result = repository.register(newUser, pass)
                    if (result.isSuccess) {
                        currentUser = result.getOrNull()
                        sampleRequestsState = repository.getSampleRequests()
                        currentScreen = AppScreen.Main
                    } else {
                        loginError = result.exceptionOrNull()?.message
                        currentScreen = AppScreen.Login
                    }
                },
                onNavigateBack = {
                    currentScreen = AppScreen.Login
                }
            )
        }

        is AppScreen.Main -> {
            val user = currentUser
            if (user == null) {
                currentScreen = AppScreen.Login
            } else {
                Crossfade(
                    targetState = currentMainTab,
                    animationSpec = tween(if (animate) 130 else 0),
                    label = "pestaña"
                ) { tab ->
                when (tab) {
                    0 -> {
                        // Menú Principal / Home
                        HomeScreen(
                            user = user,
                            lessons = repository.getAllLessons(),
                            recommendedLessons = repository.getRecommendedLessons(user.businessType),
                            onSelectLesson = { lesson ->
                                currentScreen = AppScreen.VideoDetail(lesson)
                            },
                            onToggleFavorite = { lessonId ->
                                val updated = repository.toggleFavorite(lessonId)
                                if (updated != null) currentUser = updated
                            },
                            onNavigateToCatalog = {
                                currentMainTab = 1
                            },
                            sampleRequests = sampleRequestsState,
                            currentTab = currentMainTab,
                            onSelectTab = { tab ->
                                if (tab == 3) {
                                    currentScreen = AppScreen.Profile
                                } else {
                                    currentMainTab = tab
                                }
                            }
                        )
                    }

                    1 -> {
                        // Insumos Master Martini / Catálogo
                        ProductCatalogScreen(
                            user = user,
                            products = repository.getAllProducts(),
                            onRequestSample = { product, notes ->
                                repository.createSampleRequest(product, notes)
                                sampleRequestsState = repository.getSampleRequests()
                            },
                            onNavigateBack = { currentMainTab = 0 }
                        )
                    }

                    2 -> {
                        // Favoritos / Clases Guardadas
                        SavedLessonsScreen(
                            user = user,
                            allLessons = repository.getAllLessons(),
                            onSelectLesson = { lesson ->
                                currentScreen = AppScreen.VideoDetail(lesson)
                            },
                            onToggleFavorite = { lessonId ->
                                val updated = repository.toggleFavorite(lessonId)
                                if (updated != null) currentUser = updated
                            },
                            onNavigateBack = { currentMainTab = 0 }
                        )
                    }
                }
                }
            }
        }

        is AppScreen.VideoDetail -> {
            val user = currentUser
            if (user == null) {
                currentScreen = AppScreen.Login
            } else {
                val isFav = user.favoriteLessonIds.contains(screen.lesson.id)
                val isComp = user.completedLessonIds.contains(screen.lesson.id)

                VideoDetailScreen(
                    lesson = screen.lesson,
                    user = user,
                    isFavorite = isFav,
                    isCompleted = isComp,
                    onNavigateBack = { currentScreen = AppScreen.Main },
                    onToggleFavorite = {
                        val updated = repository.toggleFavorite(screen.lesson.id)
                        if (updated != null) currentUser = updated
                    },
                    onToggleCompleted = {
                        val updated = repository.toggleCompleted(screen.lesson.id)
                        if (updated != null) currentUser = updated
                    },
                    onRequestSample = { product, notes ->
                        repository.createSampleRequest(product, notes)
                        sampleRequestsState = repository.getSampleRequests()
                    }
                )
            }
        }

        is AppScreen.Profile -> {
            val user = currentUser
            if (user == null) {
                currentScreen = AppScreen.Login
            } else {
                ProfileScreen(
                    user = user,
                    sampleRequests = sampleRequestsState,
                    onUpdateUser = { updated ->
                        repository.updateUser(updated)
                        currentUser = updated
                    },
                    onLogout = {
                        repository.logout()
                        currentUser = null
                        currentScreen = AppScreen.Login
                        currentMainTab = 0
                    },
                    onOpenMarketing = { currentScreen = AppScreen.Marketing },
                    onNavigateBack = {
                        currentScreen = AppScreen.Main
                    }
                )
            }
        }

        is AppScreen.Marketing -> {
            MarketingScreen(
                lessons = repository.getAllLessons(),
                onNavigateBack = { currentScreen = AppScreen.Profile }
            )
        }
    }
    }
    }
    }
}
