package com.listen.to.miskiatty.model.database.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
    fun fromOrdersListToJson(orders: List<Order>): String{
        val gson = Gson()
        val type = object: TypeToken<List<Order>>() {}.type
        return gson.toJson(orders, type)
    }

    @TypeConverter
    fun fromJsonToOrdersList(json: String): List<Order>{
        val gson = Gson()
        val type = object: TypeToken<List<Order>>() {}.type
        return gson.fromJson(json, type)
    }

    //Products - Json
    @TypeConverter
    fun fromProductsListToJson(products: List<Product>): String{
        val gson = Gson()
        val type = object: TypeToken<List<Product>>() {}.type
        return gson.toJson(products, type)
    }

    @TypeConverter
    fun fromJsonToProductsList(json: String): List<Product>{
        val gson = Gson()
        val type = object: TypeToken<List<Product>>() {}.type
        return gson.fromJson(json, type)
    }

    //Client - Json
    @TypeConverter
    fun fromClientToJson(client: Client): String{
        val gson = Gson()
        val type = object: TypeToken<Client>() {}.type
        return gson.toJson(client, type)
    }

    @TypeConverter
    fun fromJsonToClient(json: String): Client{
        val gson = Gson()
        val type = object: TypeToken<Client>() {}.type
        return gson.fromJson(json, type)
    }
}