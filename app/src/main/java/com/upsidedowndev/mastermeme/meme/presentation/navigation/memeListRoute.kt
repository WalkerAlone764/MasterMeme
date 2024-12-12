package com.upsidedowndev.mastermeme.meme.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.MemeListScreen


fun NavGraphBuilder.memeListRoute(
    onCreateMemeNavigation: () -> Unit
) {
    composable<Route.MemeList> {
        MemeListScreen(
            onCreateMemeNavigation = onCreateMemeNavigation
        )
    }
}