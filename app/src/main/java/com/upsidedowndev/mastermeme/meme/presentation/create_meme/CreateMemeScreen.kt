package com.upsidedowndev.mastermeme.meme.presentation.create_meme

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upsidedowndev.mastermeme.R
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.component.CreateMemeBottomBar
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.component.CreateMemeContentForStockImages
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.component.CreateMemeTopBar
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.component.SaveMemeBottomSheet
import com.upsidedowndev.mastermeme.meme.presentation.create_meme.component.TextInputDialog
import com.upsidedowndev.mastermeme.ui.theme.MasterMemeTheme
import kotlinx.coroutines.CoroutineScope


@Composable
fun CreateMemeScreen(
    modifier: Modifier = Modifier,
    uiState: CreateMemeState,
    graphicsLayer: GraphicsLayer,
    scope: CoroutineScope,
    onAction: (CreateMemeAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            CreateMemeTopBar()
        },
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            CreateMemeBottomBar(
                isTestFeatureEnabled = uiState.anyTextSelected,
                onAddTextClick = {
                    onAction(CreateMemeAction.OnClickAddText)
                },
                onClickSave = {
                    onAction(CreateMemeAction.OnClickSave)
                },
                graphicsLayer = graphicsLayer,
                scope = scope
            )
        },
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus(force = true)
                        onAction(CreateMemeAction.DismissSelection)
                        keyboardController?.hide()
                    }
                )
            }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            CreateMemeContentForStockImages(
                memeResources = R.drawable._3__43iacv,
                uiState = uiState,
//                onAction = onAction,
                graphicsLayer = graphicsLayer,
                onAction = onAction
            )


        }

        TextInputDialog(
         isVisible = uiState.textInputDialogShown,
            onCancel = {
                onAction(CreateMemeAction.OnDismissTextInputDialog)
            },
            onOk = {
                onAction(CreateMemeAction.OnCreateTextInput(it))
            }
        )

        SaveMemeBottomSheet(
            isSaveBottomSheetVisible = uiState.saveMemeBottomSheetShown,
            graphicsLayer = graphicsLayer,
            onDismissSaveBottomSheet = {
                onAction(CreateMemeAction.OnDismissSaveBottomSheet)
            },
            onSaveToDevice = {
                onAction(CreateMemeAction.SaveToDevice(graphicsLayer))
            }
        )
    }

}

@Preview
@Composable
private fun CreateMemeListPreview() {
    MasterMemeTheme {
        CreateMemeScreen(
            uiState = CreateMemeState(),
            onAction = {},
            graphicsLayer = rememberGraphicsLayer(),
            scope = rememberCoroutineScope()
        )
    }
}