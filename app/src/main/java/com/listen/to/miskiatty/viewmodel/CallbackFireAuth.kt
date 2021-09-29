package com.listen.to.miskiatty.viewmodel

interface CallbackFireAuth<T> {
    fun onSucces(result: T?)
    fun onFailure(e: Exception)
}