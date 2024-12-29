package com.upsidedowndev.mastermeme.meme.data.model

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "meme_table")
data class MemeModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val memeItem: List<MemeItem>,
    val bitmap: Bitmap,
    val defaultMemeId: Int,
    @ColumnInfo(name = "saved_path") val savedPath: String,
)

data class MemeItem(
    val text: String,
    val offset: Offset,
    @ColumnInfo(name = "font_size") val fontSize: Float,
)

