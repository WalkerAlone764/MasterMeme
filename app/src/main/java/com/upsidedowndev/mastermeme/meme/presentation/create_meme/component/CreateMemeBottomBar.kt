package com.upsidedowndev.mastermeme.meme.presentation.create_meme.component

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.mastermeme.core.presentation.MemeButton
import com.upsidedowndev.mastermeme.core.presentation.MemeOutlineButton
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun CreateMemeBottomBar(
    modifier: Modifier = Modifier,
    isTestFeatureEnabled: Boolean,
    onAddTextClick: () -> Unit,
    onClickSave: () -> Unit,
    graphicsLayer: GraphicsLayer,
    scope: CoroutineScope
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
            TextSliderBottomBar()
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
private fun TextSliderBottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close"
        )

        Row {
            Text(
                text = "Aa",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}


@Composable
private fun DefaultBottomMenu(
    onAddTextClick: () -> Unit,
    onClickSave: () -> Unit,
    graphicsLayer: GraphicsLayer,
    scope: CoroutineScope
) {

    val context = LocalContext.current
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
//                    val file = File(context.cacheDir, "images.jpg")
//                    val fout = FileOutputStream(file)
//                    val bitmap = graphicsLayer.toImageBitmap()
//                    bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fout)
//                    fout.flush()
//                    fout.close()
                }
            }
        )
    }
}


@Preview
@Composable
private fun CreateMemeBottomBarPreview() {
    MasterMemeTheme {
        TextSliderBottomBar()
    }
}