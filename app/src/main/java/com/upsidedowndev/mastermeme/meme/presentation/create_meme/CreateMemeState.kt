package com.upsidedowndev.mastermeme.meme.presentation.create_meme

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import com.upsidedowndev.mastermeme.core.presentation.MemeDefault
import com.upsidedowndev.mastermeme.core.presentation.getTemplateList

data class CreateMemeState(
    val memeTextList: List<MemeText> = emptyList<MemeText>(),
    val anyTextSelected: Boolean = false,
    val textInputDialogShown: Boolean = false,
    val saveMemeBottomSheetShown: Boolean = false,
    val backgroundMeme: MemeDefault = getTemplateList().first()
)

data class MemeText(
    val textFieldState: TextFieldState = TextFieldState(),
    val offset: Offset = Offset(0f, 100f),
    val isSelectedTextField: Boolean = false,
    val focusRequester: FocusRequester = FocusRequester(),
    val defaultFontScale: Float = 1f,
    val tempFontSize: Float = 1f

)
