package com.listen.to.wave.viewmodel

import java.lang.Exception

interface CallbackFireStore<T>{
    fun onSucces(result: T?)
    fun onFailure(e: Exception)
}