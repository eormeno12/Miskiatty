package com.listen.to.miskiatty.model.database.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import java.io.ByteArrayOutputStream
import java.lang.Byte.decode
import java.util.*

class RoomConverters {
    
    //URI - String
    @TypeConverter
    fun fromUriToString(uri: Uri): String{
        return uri.toString()
    }

    @TypeConverter
    fun fromStringToUri(string: String): Uri{
        return Uri.parse(string)
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

    //Clients - Json
    @TypeConverter
    fun fromClientsListToJson(products: List<Client>): String{
        val gson = Gson()
        val type = object: TypeToken<List<Client>>() {}.type

        return gson.toJson(products, type)
    }

    @TypeConverter
    fun fromJsonToClientsList(json: String): List<Client>{
        val gson = Gson()
        val type = object: TypeToken<List<Client>>() {}.type

        return gson.fromJson(json, type)
    }

    //List<Int> - Json
    @TypeConverter
    fun fromIntListToJson(products: List<Int>): String{
        val gson = Gson()
        val type = object: TypeToken<List<Int>>() {}.type

        return gson.toJson(products, type)
    }

    @TypeConverter
    fun fromJsonToIntList(json: String): List<Int>{
        val gson = Gson()
        val type = object: TypeToken<List<Int>>() {}.type

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