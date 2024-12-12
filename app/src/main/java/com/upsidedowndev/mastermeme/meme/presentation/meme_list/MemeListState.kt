package com.upsidedowndev.mastermeme.meme.presentation.meme_list

import com.upsidedowndev.mastermeme.meme.presentation.common.Meme

data class MemeListState(
    val isLoading: Boolean = false,
    val memes: List<Meme> = emptyList(),
)
