package com.upsidedowndev.mastermeme.meme.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.geometry.Offset
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.upsidedowndev.mastermeme.meme.data.model.MemeItem
import java.io.ByteArrayOutputStream
import java.time.LocalTime
import java.util.Date

class RoomConvertor {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
         bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun bitmapFromString(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }

    @TypeConverter
    fun fromList(value: List<MemeItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun listFromString(value: String): List<MemeItem> {
        return Gson().fromJson(value, Array<MemeItem>::class.java).toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromString(value: String): LocalTime {
        return LocalTime.parse(value)
    }

    @TypeConverter
    fun toLocalTime(value: LocalTime): String {
        return value.toString()
    }

    @TypeConverter
    fun offsetToString(offset: Offset): String {
        return "${offset.x},${offset.y}"
    }

    @TypeConverter
    fun stringToOffset(value: String): Offset {
        val (x, y) = value.split(",").map { it.toFloat() }
        return Offset(x, y)
    }
}
