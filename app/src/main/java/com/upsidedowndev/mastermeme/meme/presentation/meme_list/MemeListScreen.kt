@file:Suppress("DEPRECATION")

package com.upsidedowndev.mastermeme.meme.presentation.meme_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.component.EmptyListComponent
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.component.MemeListFloatingActionButton
import com.upsidedowndev.mastermeme.meme.presentation.meme_list.component.MemeListTopBar
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MemeListScreen(
    modifier: Modifier = Modifier,
    onCreateMemeNavigation: () -> Unit
) {

    Scaffold(
        topBar = { MemeListTopBar() },
        floatingActionButton = {
            MemeListFloatingActionButton(
                onClick = {
                    onCreateMemeNavigation()
                }
            )
        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            EmptyListComponent()

        }
    }

}


@PreviewLightDark
@Composable
private fun MemeListScreenPreview() {
    MasterMemeTheme {
        MemeListScreen(
            onCreateMemeNavigation = {}
        )
    }
}