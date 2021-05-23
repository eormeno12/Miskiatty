package com.listen.to.miskiatty.model.network.storage

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.listen.to.miskiatty.viewmodel.CallbackFireStorage
import com.listen.to.wave.viewmodel.CallbackFireStore
import java.util.*

class FireStorageService(private val storage: FirebaseStorage = Firebase.storage("gs://miskiatty.appspot.com")) {
    val storageRef = storage.reference
    val imagesRef = storageRef.child("images")

    fun uploadFileFromUri(uri: Uri, callbackFireStore: CallbackFireStorage<String>) {
        val ref = System.currentTimeMillis().toString()
        val fileRef = imagesRef.child(ref)
        val uploadTask = fileRef.putFile(uri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            fileRef.downloadUrl.addOnSuccessListener {
                callbackFireStore.onSucces(it.toString())
            }
        }. addOnProgressListener {
            val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
            callbackFireStore.onProgress(progress)
        }.addOnFailureListener {
            callbackFireStore.onFailure(it)
        }
    }
}