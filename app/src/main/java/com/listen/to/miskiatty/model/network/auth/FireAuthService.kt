package com.listen.to.miskiatty.model.network.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.listen.to.wave.viewmodel.CallbackFireAuth
import java.lang.Exception

class FireAuthService (var fireAuth: FirebaseAuth){

    init {
        fireAuth = Firebase.auth
    }

    fun verifyIfUserIsSigned(callbackFireAuth: CallbackFireAuth<FirebaseUser>){
        val user = fireAuth.currentUser

        try {
            callbackFireAuth.onSucces(user)
        }catch (e: Exception){
            callbackFireAuth.onFailure(e)
        }
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