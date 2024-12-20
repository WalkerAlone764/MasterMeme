package com.upsidedowndev.mastermeme.meme.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.upsidedowndev.mastermeme.meme.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class RepositoryImpl(
    private val context: Context
) : Repository {


    override suspend fun saveToAppCache(
        bitmap: Bitmap
    ) {
        val file = context.filesDir

        withContext(Dispatchers.IO) {
            val tempFile = File(file, "meme_${file.list()?.size}.txt")
            tempFile.outputStream().use { output ->
                bitmap.compress(
                    Bitmap.CompressFormat.PNG, 100, output
                )
            }

        }

    }

    override fun saveToImageStorage() {
        TODO("Not yet implemented")
    }
}