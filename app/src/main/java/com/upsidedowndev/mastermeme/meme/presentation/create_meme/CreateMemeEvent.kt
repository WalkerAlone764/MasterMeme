package com.upsidedowndev.mastermeme.meme.presentation.create_meme

sealed interface CreateMemeEvent {
    data object SuccessfullySavedMeme: CreateMemeEvent
    data object NavigateBack: CreateMemeEvent
    data object SavedInDB: CreateMemeEvent
}