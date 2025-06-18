package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.newsapp.presentation.navgraph.NavGraph
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var useCases: AppEntryUseCases

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            NewsAppTheme {

                val isSystemDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()

                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemDarkMode
                    )

                    //setSystemBarsAppearance(darkIcons = !isSystemDarkMode)
                }



                Box(modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                ){
                    val startDestination = viewModel.stateDestination
                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }

//    fun Activity.setSystemBarsAppearance(darkIcons: Boolean) {
//        val window = this.window
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        val controller = WindowInsetsControllerCompat(window, window.decorView)
//
//        // Set transparent background
//        window.statusBarColor = android.graphics.Color.TRANSPARENT
//        window.navigationBarColor = android.graphics.Color.TRANSPARENT
//
//        // Control icon color
//        controller.isAppearanceLightStatusBars = darkIcons
//        controller.isAppearanceLightNavigationBars = darkIcons
//    }
}

