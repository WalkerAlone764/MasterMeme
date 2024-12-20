package com.upsidedowndev.mastermeme.meme.domain.repository

import android.graphics.Bitmap

interface Repository {

    suspend fun saveToAppCache(
        bitmap: Bitmap
    )

    fun saveToImageStorage()
}