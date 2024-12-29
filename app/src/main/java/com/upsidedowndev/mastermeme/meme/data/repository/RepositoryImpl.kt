package com.upsidedowndev.mastermeme.meme.data.repository

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.upsidedowndev.mastermeme.meme.data.dao.MemeDao
import com.upsidedowndev.mastermeme.meme.data.model.MemeModel
import com.upsidedowndev.mastermeme.meme.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val context: Context,
    private val memeDao: MemeDao
) : Repository {


    override suspend fun saveMemeToDevice(
        bitmap: Bitmap
    ): String {
        val cacheDir = context.cacheDir

        return withContext(Dispatchers.IO) {

            val files = cacheDir.listFiles()?.filter { it ->
                Log.d("list name: ", it.name)
                it.name.contains("meme")
            }
            val file = File(context.cacheDir, "meme_${files?.size ?: 0}.jpg")
            val fout = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout)
            fout.flush()
            fout.close()

            val path =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/MasterMeme")
            path.mkdir()
            val externalFile = File(path, file.name)
            externalFile.createNewFile()
            val externalFileOut = FileOutputStream(externalFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, externalFileOut)
            externalFileOut.flush()
            externalFileOut.close()

            return@withContext externalFile.path
        }

    }

    override suspend fun shareMemeWithApp(
        bitmap: Bitmap
    ): String {
       return withContext(
            Dispatchers.IO
        ) {
            try {
//            val sendIntent: Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
//                type = "text/plain"
//            }
//
//            val shareIntent = Intent.createChooser(sendIntent, null)
//            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(shareIntent)

                val file = File(context.cacheDir, "temp.jpg")
                val fout = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout)
                fout.flush()
                fout.close()

                file.createNewFile()

                val contentUri = FileProvider.getUriForFile(
                    context,
                    "com.upsidedowndev.mastermeme.fileprovider",
                    file
                )

                Log.d("content URI ", contentUri.toString())

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    // Example: content://com.google.android.apps.photos.contentprovider/...
                    putExtra(Intent.EXTRA_STREAM, contentUri)
                    type = "image/jpeg"
                }


                val shareIntent = Intent.createChooser(sendIntent, null)
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(shareIntent)

                return@withContext file.path

            } catch (e: Exception) {
                Log.d("e", e.toString())
                throw e

            }
        }
    }

    override suspend fun insertMeme(memeModel: MemeModel) {
        withContext(Dispatchers.IO) {
            memeDao.insertMeme(memeModel)
        }
    }

    override fun getAllMeme(): Flow<List<MemeModel>> {
        return memeDao.getAllMeme()
    }

    override suspend fun getMemeById(memeId: Int): MemeModel {
        return memeDao.getMemeById(memeId)
    }

}