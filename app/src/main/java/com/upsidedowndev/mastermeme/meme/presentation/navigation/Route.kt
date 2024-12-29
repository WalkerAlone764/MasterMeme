package com.upsidedowndev.mastermeme.meme.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object MemeList: Route()

    @Serializable
    data class CreateMeme(val memeId: Int,val memeDefaultId: Int, val memeStatus: MemeStatus): Route()
}

enum class MemeStatus {
    CREATE,
    EDIT
}