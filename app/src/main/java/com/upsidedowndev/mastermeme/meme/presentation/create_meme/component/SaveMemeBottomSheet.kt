@file:OptIn(ExperimentalMaterial3Api::class)

package com.upsidedowndev.mastermeme.meme.presentation.create_meme.component

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.mastermeme.R
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme
import com.upsidedowndev.mastermeme.ui.theme.manropeFamily
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveMemeBottomSheet(
    isSaveBottomSheetVisible: Boolean,
    graphicsLayer: GraphicsLayer,
    onDismissSaveBottomSheet: () -> Unit,
    onSaveToDevice: () -> Unit,
    onShareMeme: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    if (isSaveBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismissSaveBottomSheet()
            },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {

            SaveMemeItem(
                icon = R.drawable.save_icon,
                title = "Save to device",
                description = "Save created meme in the Files of your device",
                onClick = {
                    onSaveToDevice()
                }
            )
            Spacer(
                modifier = Modifier.height(18.dp)
            )
            SaveMemeItem(icon = R.drawable.share_icon,
                title = "Share the meme",
                description = "Share your meme or open it in the other App",
                onClick = {
//                    scope.launch {
//                        val file = File(context.cacheDir, "images.jpg")
//                        val fout = FileOutputStream(file)
//                        val bitmap = graphicsLayer.toImageBitmap()
//                        bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fout)
//                        fout.flush()
//                        fout.close()
//
//
//                        val share = Intent(Intent.ACTION_SEND)
//                        share.setType("image/jpeg")
//                        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.path))
//                        context.startService(Intent.createChooser(share,"share Image"))
//                    }

                    onShareMeme()
                })


        }
    }
}

@Composable
private fun SaveMemeItem(
    @DrawableRes icon: Int, title: String, description: String, onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            )
            .clickable {
                onClick()
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(
            modifier = Modifier.width(16.dp)
        )
        Column {
            Text(
                text = title,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = description,
                fontFamily = manropeFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}


@Preview
@Composable
private fun SaveMemeItemPrev() {
    MasterMemeTheme {
        SaveMemeItem(
            icon = R.drawable.save_icon,
            title = "Save to device",
            description = "Save created meme in the Files of your device"
        )
    }
}


//@Preview
//@Composable
//private fun SaveMemeBottomSheetPrev() {
//        MasterMemeTheme {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                SaveMemeBottomSheet(
//                    isSaveBottomSheetVisible = true,
//                    onDismissSaveBottomSheet = {}
//                )
//            }
//        }
//}