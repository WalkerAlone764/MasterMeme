package com.upsidedowndev.mastermeme.meme.presentation.meme_list

import com.upsidedowndev.mastermeme.meme.presentation.navigation.MemeStatus

sealed interface MemeListEvent {
    data class NavigateToCreate(val memeId: Int,val defaultMeme:Int, val memeStatus: MemeStatus) : MemeListEvent
}
