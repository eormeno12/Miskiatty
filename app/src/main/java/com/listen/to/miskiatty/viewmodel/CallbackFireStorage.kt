package com.listen.to.miskiatty.viewmodel

interface CallbackFireStorage<T>{
    fun onSucces(result: T?)
    fun onProgress(progress: Double)
    fun onFailure(e: Exception)
}