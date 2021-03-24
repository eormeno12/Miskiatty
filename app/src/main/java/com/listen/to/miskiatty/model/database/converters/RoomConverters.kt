package com.listen.to.miskiatty.model.database.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.listen.to.miskiatty.model.database.Order
import java.io.ByteArrayOutputStream

class RoomConverters {

    //Bitmap - ByteArray
    @TypeConverter
    fun fromBitmapToByteArray(bitmap: Bitmap): ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun fromByteArrayToBitmap(byteArray: ByteArray): Bitmap{
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    //Orders - Json
    @TypeConverter
    fun fromClientsListToJson(orders: List<Order>): String{
        val gson = Gson()
        val type = object: TypeToken<List<Order>>() {}.type
        return gson.toJson(orders, type)
    }

    @TypeConverter
    fun fromJsonToClientsList(json: String): List<Order>{
        val gson = Gson()
        val type = object: TypeToken<List<Order>>() {}.type
        return gson.fromJson(json, type)
    }
}