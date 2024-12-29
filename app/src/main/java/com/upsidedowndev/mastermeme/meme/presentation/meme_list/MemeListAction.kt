package com.upsidedowndev.mastermeme.meme.presentation.meme_list

import com.upsidedowndev.mastermeme.meme.presentation.common.Meme
import com.upsidedowndev.mastermeme.meme.presentation.navigation.MemeStatus

sealed class MemeListAction {
    data object OnAddMeme: MemeListAction()
    data class OnClickMeme(val memeId: Int,val memeDefaultId: Int, val memeStatus: MemeStatus): MemeListAction()
}