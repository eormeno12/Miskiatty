package com.listen.to.miskiatty.viewmodel

interface CallbackFireStore<T>{
    fun onSucces(result: T?)
    fun onFailure(e: Exception)
}