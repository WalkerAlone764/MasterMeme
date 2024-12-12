package com.upsidedowndev.mastermeme.meme.presentation.meme_list

sealed class MemeListAction {
    data object OnAddMeme: MemeListAction()
}