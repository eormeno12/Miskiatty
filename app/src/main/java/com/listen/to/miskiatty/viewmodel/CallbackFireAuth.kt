package com.listen.to.wave.viewmodel

import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

interface CallbackFireAuth<T> {
    fun onSucces(result: T?)
    fun onFailure(e: Exception)
}