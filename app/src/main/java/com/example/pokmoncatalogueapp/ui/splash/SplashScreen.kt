package com.example.pokmoncatalogueapp.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pokmoncatalogueapp.R
import com.example.pokmoncatalogueapp.constant.DELAY_3_M
import com.example.pokmoncatalogueapp.ui.nav.CATALOGUE
import com.example.pokmoncatalogueapp.ui.nav.SPLASH
import com.example.pokmoncatalogueapp.ui.theme.PokemonRed
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_loading))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    LaunchedEffect(key1 = true) {
        delay(DELAY_3_M)

        navController.navigate(CATALOGUE) {
            popUpTo(SPLASH) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PokemonRed),
        contentAlignment = Alignment.Center
    ) {
        if (composition != null) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(dimensionResource(id = R.dimen.default_icon_size))
            )
        }
    }
}