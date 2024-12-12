package com.upsidedowndev.mastermeme.meme.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.CreateMemeScreen

fun NavGraphBuilder.createMemeRoute() {
    composable<Route.CreateMeme> {
//        val args = it.toRoute<Route.CreateMeme>()
//        Text("hello world ${args.memeStatus} ${args.memeId}")

        CreateMemeScreen()
    }
}