package com.upsidedowndev.mastermeme.meme.presentation.meme_list.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme

@Composable
fun MemeListFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
//        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Meme"
        )
    }

}

@Preview
@Composable
private fun MemeListFloatingActionButtonPreview() {
    MasterMemeTheme {
        MemeListFloatingActionButton(
            onClick = {}
        )
    }
}