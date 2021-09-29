package com.listen.to.miskiatty.model.network.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.listen.to.miskiatty.viewmodel.CallbackFireAuth

class FireAuthService (private var fireAuth: FirebaseAuth){

    init {
        fireAuth = Firebase.auth
    }

    fun userLogin(email: String, password: String, callbackFireAuth: CallbackFireAuth<FirebaseUser>){
        fireAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                val user = fireAuth.currentUser
                callbackFireAuth.onSucces(user)
            }else{
                callbackFireAuth.onFailure(task.exception!!)
            }
        }
    }
}