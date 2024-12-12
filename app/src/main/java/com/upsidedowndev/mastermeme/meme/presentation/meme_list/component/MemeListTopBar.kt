@file:OptIn(ExperimentalMaterial3Api::class)

package com.upsidedowndev.mastermeme.meme.presentation.meme_list.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme
import com.upsidedowndev.mastermeme.ui.theme.MyAppColor
import com.upsidedowndev.mastermeme.ui.theme.manropeFamily

@Composable
fun MemeListTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "Your Memes",
                fontWeight = FontWeight.SemiBold,
                fontFamily = manropeFamily,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    )

}

@PreviewLightDark
@Composable
private fun MemeListTopBarPreview() {
    MasterMemeTheme {
        MemeListTopBar()

    }
}