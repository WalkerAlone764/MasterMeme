package com.upsidedowndev.mastermeme.meme.presentation.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.CreateMemeScreen
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.CreateMemeViewModel

fun NavGraphBuilder.createMemeRoute() {
    composable<Route.CreateMeme> {
//        val args = it.toRoute<Route.CreateMeme>()
//        Text("hello world ${args.memeStatus} ${args.memeId}")


        val viewmodel = hiltViewModel<CreateMemeViewModel>()
        val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()
        val graphicsLayer = rememberGraphicsLayer()

        CreateMemeScreen(
            uiState = uiState.value,
            onAction = viewmodel::onAction,
            graphicsLayer = graphicsLayer,
            scope = scope
        )
    }
}