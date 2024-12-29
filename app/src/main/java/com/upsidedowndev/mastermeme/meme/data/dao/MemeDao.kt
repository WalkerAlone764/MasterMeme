package com.upsidedowndev.mastermeme.meme.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upsidedowndev.mastermeme.meme.data.model.MemeModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeme(memeModel: MemeModel): Long

    @Query("select * from meme_table")
    fun getAllMeme(): Flow<List<MemeModel>>

    @Query("select * from meme_table where id=:memeId")
    suspend fun getMemeById(memeId:Int): MemeModel
}