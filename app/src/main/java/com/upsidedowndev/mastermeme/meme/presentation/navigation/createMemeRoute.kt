package com.upsidedowndev.mastermeme.meme.presentation.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.upsidedowndev.mastermeme.core.presentation.ObserveAsEvent
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.CreateMemeEvent
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.CreateMemeScreen
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.CreateMemeViewModel

fun NavGraphBuilder.createMemeRoute(
    navController: NavHostController
) {
    composable<Route.CreateMeme> {
//        val args = it.toRoute<Route.CreateMeme>()
//        Text("hello world ${args.memeStatus} ${args.memeId}")


        val viewmodel = hiltViewModel<CreateMemeViewModel>()
        val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()
        val graphicsLayer = rememberGraphicsLayer()
        val context = LocalContext.current

        LaunchedEffect(viewmodel.createMeme.memeStatus) {
            Log.d("status", viewmodel.createMeme
                .memeStatus.name)
        }


        ObserveAsEvent(viewmodel.uiEvent) { event ->
            when (event) {
                CreateMemeEvent.SuccessfullySavedMeme -> {
                    Toast.makeText(
                        context, "Successfully Saved Meme", Toast.LENGTH_SHORT
                    ).show()

                    navController.navigateUp()
                }

                CreateMemeEvent.NavigateBack -> {
                    navController.navigateUp()
                }
                CreateMemeEvent.SavedInDB -> {
                    Toast.makeText(
                        context, "meme has been saved", Toast.LENGTH_SHORT

                    ).show()
                }
            }
        }


        CreateMemeScreen(
            uiState = uiState.value,
            onAction = viewmodel::onAction,
            graphicsLayer = graphicsLayer,
            scope = scope,
            onClickBack = {
                navController.navigateUp()
            }
        )
    }
}