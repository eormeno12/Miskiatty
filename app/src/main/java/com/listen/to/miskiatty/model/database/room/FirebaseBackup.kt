package com.listen.to.miskiatty.model.database.room

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.google.firebase.firestore.FirebaseFirestore
import com.listen.to.miskiatty.model.database.converters.RoomConverters
import com.listen.to.miskiatty.model.network.FireStoreService
import com.listen.to.miskiatty.model.network.UserData
import com.listen.to.miskiatty.model.provider.PreferenceProvider
import com.listen.to.wave.viewmodel.CallbackFireStore

import kotlinx.coroutines.launch
import java.lang.Exception

class FirebaseBackup(val activity: Activity, val lifecycle: Lifecycle) {
    private val roomConverters = RoomConverters()
    private val fireStoreService = FireStoreService(FirebaseFirestore.getInstance())
    private val db = RoomDb.getDatabase(activity)

    fun uploadBackup(){
        var productsJSON = ""
        var clientsJSON = ""
        var ordersJSON = ""

        lifecycle.coroutineScope.launch {
            val productsRoom = db.productDao().getAllProducts()
            val ordersRoom = db.orderDao().getAllOrders()
            val clientsRoom = db.clientDao().getAllClients()

            productsJSON = roomConverters.fromProductsListToJson(productsRoom)
            clientsJSON = roomConverters.fromClientsListToJson(clientsRoom)
            ordersJSON = roomConverters.fromOrdersListToJson(ordersRoom)

            updateFieldFirebase(activity, "productsJSON", productsJSON)
            updateFieldFirebase(activity, "clientsJSON", clientsJSON)
            updateFieldFirebase(activity, "ordersJSON", ordersJSON)
        }
    }

    private fun updateFieldFirebase(activity: Activity, field: String, data: String){
        val preferenceProvider = PreferenceProvider(activity.applicationContext)
        val email = preferenceProvider.getEmailLogin()

        fireStoreService.updateUser(email!!, field, data, object: CallbackFireStore<String>{
            override fun onSucces(result: String?) {
                //TO DO
            }

            override fun onFailure(e: Exception) {
                //TO DO
            }
        })
    }

    fun chargeBackup(email: String){
        fireStoreService.findUserByEmail(email, object: CallbackFireStore<UserData>{
            override fun onSucces(result: UserData?) {
                lifecycle.coroutineScope.launch {
                     if(result != null){
                        db.clearAllData()

                        if (result.productsJSON.isNotEmpty()){
                            val products = roomConverters.fromJsonToProductsList(result.productsJSON!!)
                            db.productDao().addProductsList(products)
                        }

                        if (result.clientsJSON.isNotEmpty()){
                            val clients = roomConverters.fromJsonToClientsList(result.clientsJSON!!)
                            db.clientDao().addClientsList(clients)
                        }

                        if (result.ordersJSON.isNotEmpty()){
                            val orders = roomConverters.fromJsonToOrdersList(result.ordersJSON!!)
                            db.orderDao().addOrdersList(orders)
                        }
                    }
                }
            }

            override fun onFailure(e: Exception) {
                //TO DO
            }

        })

    }
}