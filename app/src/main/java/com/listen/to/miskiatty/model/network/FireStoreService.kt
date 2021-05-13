package com.listen.to.miskiatty.model.network

import com.google.firebase.firestore.FirebaseFirestore
import com.listen.to.wave.viewmodel.CallbackFireStore

val COLLECTION_USERS = "users"
class FireStoreService(val firebaseFirestore: FirebaseFirestore) {
    fun setDocument(documentName: String, data: Any, callbackFireStore: CallbackFireStore<Void>){
        firebaseFirestore.collection(COLLECTION_USERS).document(documentName).set(data).addOnSuccessListener {
            callbackFireStore.onSucces(null)
        }.addOnFailureListener { exception ->
            callbackFireStore.onFailure(exception)
        }
    }

    fun updateUser(email: String, field: String, parameter: Any, callbackFireStore: CallbackFireStore<String>){
        firebaseFirestore.collection(COLLECTION_USERS).document(email).update(field, parameter).addOnSuccessListener {
            callbackFireStore.onSucces(null)
        }.addOnFailureListener { exception ->
            callbackFireStore.onFailure(exception)
        }
    }

    fun findUserByEmail(email: String, callbackFireStore: CallbackFireStore<UserData>){
        firebaseFirestore.collection(COLLECTION_USERS).document(email).get().addOnSuccessListener {result ->

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