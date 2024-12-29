package com.upsidedowndev.mastermeme.meme.presentation.create_meme

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
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
    onAction: (CreateMemeAction) -> Unit,
    onClickBack: () -> Unit
) {

    LaunchedEffect(uiState) {
        Log.d("is Test", uiState.anyTextSelected.toString())
        val filter = uiState.memeTextList.filter { meme ->
            meme.isSelectedTextField
        }

        Log.d("is Test filter: ", filter.size.toString())
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            CreateMemeTopBar(
                onClickBack = onClickBack
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            CreateMemeBottomBar(
                isTestFeatureEnabled = uiState.memeTextList.any { meme ->
                    meme.isSelectedTextField
                },
                fontSize = uiState.memeTextList.firstOrNull { meme ->
                    meme.isSelectedTextField
                }?.tempFontSize ?: 1f,

                onAddTextClick = {
                    onAction(CreateMemeAction.OnClickAddText)
                },
                onClickSave = {
                    onAction(CreateMemeAction.OnClickSave(graphicsLayer))
                },
                graphicsLayer = graphicsLayer,
                scope = scope,
                onUpdateFontSize = {
                    onAction(CreateMemeAction.OnUpdateFontSize(it))
                },
                onDismissFontSize = {
                    onAction(CreateMemeAction.OnDismissFontSizeChange)
                },
                onSuccessFontSize = {
                    onAction(CreateMemeAction.OnSubmitFontSizeChange)
                }
            )
        },
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus(force = true)
                        onAction(CreateMemeAction.DismissSelection)
                        onAction(CreateMemeAction.OnDismissFontSizeChange)
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
            },
            onShareMeme = {
                onAction(CreateMemeAction.ShareMeme(graphicsLayer))
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
            scope = rememberCoroutineScope(),
            onClickBack = {}
        )
    }
}