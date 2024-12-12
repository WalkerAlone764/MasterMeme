package com.upsidedowndev.mastermeme.meme.presentation.meme_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upsidedowndev.mastermeme.R
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme
import com.upsidedowndev.mastermeme.ui.theme.manropeFamily

@Composable
fun EmptyListComponent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.meme_logo),
                contentDescription = null
            )
            Text(
                text = "Tap + button to create your first meme",
                fontFamily = manropeFamily,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}


@Preview
@Composable
private fun EmptyListPreview() {
    MasterMemeTheme {
        EmptyListComponent()
    }
}