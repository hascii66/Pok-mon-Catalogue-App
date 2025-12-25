package com.example.pokmoncatalogueapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokmoncatalogueapp.di.AppModule
import com.example.pokmoncatalogueapp.ui.backpack.BackpackScreen
import com.example.pokmoncatalogueapp.ui.catalogue.CatalogueScreen
import com.example.pokmoncatalogueapp.ui.nav.BACKPACK
import com.example.pokmoncatalogueapp.ui.nav.BottomNavBar
import com.example.pokmoncatalogueapp.ui.nav.CATALOGUE
import com.example.pokmoncatalogueapp.ui.nav.SPLASH
import com.example.pokmoncatalogueapp.ui.splash.SplashScreen
import com.example.pokmoncatalogueapp.ui.theme.AppColorScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppModule.initialize(applicationContext)

        setContent {
            MaterialTheme(colorScheme = AppColorScheme) {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        if (currentRoute != SPLASH) {
                            BottomNavBar(navController)
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = SPLASH,
                        modifier = Modifier.padding(if (currentRoute == SPLASH) PaddingValues(0.dp) else padding)
                    ) {
                        composable(SPLASH) {
                            SplashScreen(navController = navController)
                        }

                        composable(CATALOGUE) {
                            CatalogueScreen()
                        }

                        composable(BACKPACK) {
                            BackpackScreen()
                        }
                    }
                }
            }
        }
    }
}
