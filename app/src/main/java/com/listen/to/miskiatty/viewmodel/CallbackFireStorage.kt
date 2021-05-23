package com.listen.to.miskiatty.viewmodel

import java.lang.Exception

interface CallbackFireStorage<T>{
    fun onSucces(result: T?)
    fun onProgress(progress: Double)
    fun onFailure(e: Exception)
}