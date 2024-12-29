package com.upsidedowndev.mastermeme.meme.domain.repository

import android.graphics.Bitmap
import com.upsidedowndev.mastermeme.meme.data.model.MemeModel
import com.upsidedowndev.mastermeme.meme.presentation.common.Meme
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun saveMemeToDevice(
        bitmap: Bitmap
    ): String


    suspend fun shareMemeWithApp(
        bitmap: Bitmap
    ): String


    suspend fun insertMeme(memeModel: MemeModel)

    fun getAllMeme(): Flow<List<MemeModel>>

    suspend fun getMemeById(memeId:Int): MemeModel

}