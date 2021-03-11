package com.listen.to.miskiatty.model

import androidx.databinding.BaseObservable

class LoginObservable: BaseObservable(){

    fun userLogin(user: String, password: String): Boolean{
        return DataValidation().validateLogin(user, password)
    }
}