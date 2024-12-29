package com.upsidedowndev.mastermeme.meme.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.upsidedowndev.mastermeme.core.presentation.ObserveAsEvent
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.MemeListEvent
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.MemeListScreen
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.MemeListViewModel


fun NavGraphBuilder.memeListRoute(
    onCreateMemeNavigation: (Int,Int,MemeStatus) -> Unit
) {
    composable<Route.MemeList> {

        val memeListViewModel = hiltViewModel<MemeListViewModel>()
        val uiState by memeListViewModel.uiState.collectAsStateWithLifecycle()

        val memeList by memeListViewModel.memeList.collectAsStateWithLifecycle()

        ObserveAsEvent(memeListViewModel.uiEvent) { event ->
            when(event) {
                is MemeListEvent.NavigateToCreate -> {
                    onCreateMemeNavigation(event.memeId,event.defaultMeme, event.memeStatus)
                }
            }
        }

        MemeListScreen(
            memeList = memeList,
            memeState = uiState,
            onAction = memeListViewModel::onAction,
            navigateToCreateMeme = onCreateMemeNavigation
        )
    }
}