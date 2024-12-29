package com.upsidedowndev.mastermeme.meme.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.upsidedowndev.mastermeme.meme.data.RoomConvertor
import com.upsidedowndev.mastermeme.meme.data.dao.MemeDao
import com.upsidedowndev.mastermeme.meme.data.model.MemeModel


@TypeConverters(RoomConvertor::class)
@Database(entities = [MemeModel::class], version = 2)
abstract class MemeDatabase : RoomDatabase() {
    abstract fun memeDao(): MemeDao
}