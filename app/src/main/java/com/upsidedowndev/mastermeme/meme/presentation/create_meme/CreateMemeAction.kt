package com.upsidedowndev.mastermeme.meme.presentation.create_meme

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.layer.GraphicsLayer

sealed class CreateMemeAction {
    data object OnClickAddText : CreateMemeAction()
    data class OnDragText(val offset: Offset, val memeText: MemeText): CreateMemeAction()
    data object DismissSelection: CreateMemeAction()
    data class OnSelectText(val memeText: MemeText): CreateMemeAction()
    data class OnCreateTextInput(val text: String): CreateMemeAction()
    data object OnDismissTextInputDialog: CreateMemeAction()
    data class OnClickSave(val graphicsLayer: GraphicsLayer): CreateMemeAction()
    data object OnDismissSaveBottomSheet: CreateMemeAction()
    data class OnUpdatePosition(val offset: Offset, val memeText: MemeText): CreateMemeAction()
    data class SaveToDevice(val graphicsLayer: GraphicsLayer): CreateMemeAction()
    data class ShareMeme(val graphicsLayer: GraphicsLayer): CreateMemeAction()
    data class OnUpdateFontSize(val size: Float): CreateMemeAction()
    data object OnDismissFontSizeChange: CreateMemeAction()
    data object OnSubmitFontSizeChange: CreateMemeAction()
}