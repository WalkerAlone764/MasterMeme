package com.upsidedowndev.mastermeme.meme.presentation.create_meme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.component.CreateMemeTopBar
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme


@Composable
fun CreateMemeScreen(
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            CreateMemeTopBar()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
        ) {

        }
    }

}

@Preview
@Composable
private fun CreateMemeListPreview() {
    MasterMemeTheme {
        CreateMemeScreen()
    }
}