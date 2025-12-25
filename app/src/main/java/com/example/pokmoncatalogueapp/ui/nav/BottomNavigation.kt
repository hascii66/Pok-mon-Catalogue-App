package com.example.pokmoncatalogueapp.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pokmoncatalogueapp.R

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar(containerColor = Color.White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
            icon = { Icon(Icons.Default.List, "Catalogue") },
            label = { Text(stringResource(R.string.pokedex)) },
            selected = currentRoute == CATALOGUE,
            onClick = {
                navController.navigate(CATALOGUE) {
                    popUpTo(CATALOGUE) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Backpack, "Backpack") },
            label = { Text(stringResource(R.string.backpack)) },
            selected = currentRoute == BACKPACK,
            onClick = {
                navController.navigate(BACKPACK) {
                    popUpTo(CATALOGUE) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}