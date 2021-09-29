package com.listen.to.miskiatty.model.network.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.listen.to.miskiatty.model.network.UserData
import com.listen.to.miskiatty.viewmodel.CallbackFireStore

private const val COLLECTION_USERS = "users"

class FireStoreService(private val firebaseFirestore: FirebaseFirestore) {
    fun updateUser(email: String, field: String, parameter: Any, callbackFireStore: CallbackFireStore<String>){
        firebaseFirestore.collection(COLLECTION_USERS).document(email).update(field, parameter).addOnSuccessListener {
            callbackFireStore.onSucces(null)
        }.addOnFailureListener { exception ->
            callbackFireStore.onFailure(exception)
        }
    }

    fun findUserByEmail(email: String, callbackFireStore: CallbackFireStore<UserData>){
        firebaseFirestore.collection(COLLECTION_USERS).document(email).get().addOnSuccessListener { result ->

            if(result != null){
                callbackFireStore.onSucces(result.toObject(UserData::class.java))
            }else{
                callbackFireStore.onSucces(null)
            }

        }.addOnFailureListener { exception ->
            callbackFireStore.onFailure(exception)
        }
    }
}