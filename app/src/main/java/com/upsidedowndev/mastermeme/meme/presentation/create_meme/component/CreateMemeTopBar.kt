@file:OptIn(ExperimentalMaterial3Api::class)

package com.upsidedowndev.mastermeme.meme.presentation.create_meme.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme
import com.upsidedowndev.mastermeme.ui.theme.manropeFamily

@Composable
fun CreateMemeTopBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "New Meme",
                fontFamily = manropeFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    )
}

@Preview
@Composable
private fun CreateMemeTopBarPreview() {
    MasterMemeTheme {
        CreateMemeTopBar()
    }
}