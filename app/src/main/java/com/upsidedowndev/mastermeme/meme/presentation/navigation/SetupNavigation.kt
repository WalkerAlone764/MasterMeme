package com.upsidedowndev.mastermeme.meme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.MemeList
    ) {
        memeListRoute(
            onCreateMemeNavigation = {
                navController.navigate(Route.CreateMeme(
                    memeId = 1,
                    memeStatus = MemeStatus.STORED
                ))
            }
        )


        createMemeRoute()
    }
}