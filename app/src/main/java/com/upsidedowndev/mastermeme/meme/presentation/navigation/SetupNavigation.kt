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
            onCreateMemeNavigation = { memeId,memeDefaultId, memeStatus ->
                navController.navigate(Route.CreateMeme(
                    memeId = memeId,
                    memeStatus = memeStatus,
                    memeDefaultId = memeDefaultId
                ))
            }
        )


        createMemeRoute(
            navController = navController
        )
    }
}