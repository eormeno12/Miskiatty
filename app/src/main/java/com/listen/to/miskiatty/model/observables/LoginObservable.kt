package com.listen.to.miskiatty.model.observables

import androidx.databinding.BaseObservable
import com.listen.to.miskiatty.model.DataValidation

class LoginObservable: BaseObservable(){

    fun userLogin(user: String, password: String): Boolean{
        return DataValidation().validateLogin(user, password)
    }
}