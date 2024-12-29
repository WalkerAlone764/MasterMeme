package com.upsidedowndev.mastermeme.meme.presentation.create_meme.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.mastermeme.core.presentation.MemeButton
import com.upsidedowndev.mastermeme.core.presentation.MemeOutlineButton
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CreateMemeBottomBar(
    modifier: Modifier = Modifier,
    isTestFeatureEnabled: Boolean,
    fontSize: Float,
    graphicsLayer: GraphicsLayer,
    scope: CoroutineScope,
    onAddTextClick: () -> Unit,
    onClickSave: () -> Unit,
    onUpdateFontSize: (Float) -> Unit,
    onDismissFontSize: () -> Unit,
    onSuccessFontSize: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(
                horizontal = 16.dp,
            ),

        ) {

        if (isTestFeatureEnabled) {
            TextSliderBottomBar(

                fontSize = fontSize,
                onUpdateFontSize = onUpdateFontSize,
                onDismissFontSize = onDismissFontSize,
                onSuccessFontSize = onSuccessFontSize
            )
        } else {
            DefaultBottomMenu(
                onAddTextClick = onAddTextClick,
                onClickSave = onClickSave,
                graphicsLayer = graphicsLayer,
                scope = scope
            )
        }
    }
}


@Composable
private fun TextSliderBottomBar(
    fontSize: Float = 1f,
    onUpdateFontSize: (Float) -> Unit,
    onDismissFontSize: () -> Unit,
    onSuccessFontSize: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            modifier = Modifier
                .clickable {
                    onDismissFontSize()
                }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Aa",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
            Slider(
                value = fontSize,
                onValueChange = {
                    onUpdateFontSize(it)
                },
                valueRange = 0.5f..2f,
                steps = 0,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
            )

            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )

            Text(
                text = "Aa",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

        }

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Check",
            modifier = Modifier
                .clickable {
                    onSuccessFontSize()
                }
        )

    }
}


@Composable
private fun DefaultBottomMenu(
    onAddTextClick: () -> Unit,
    onClickSave: () -> Unit,
    graphicsLayer: GraphicsLayer,
    scope: CoroutineScope
) {

    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        MemeOutlineButton(
            onClick = {
                // Handle add text button click
                onAddTextClick()
            }
        )
        Spacer(
            modifier = Modifier
                .width(28.dp)
        )
        MemeButton(
            title = "Save Meme",
            onClick = {
                // Handle add image button click
                onClickSave()
                scope.launch {

                }
            }
        )
    }
}
