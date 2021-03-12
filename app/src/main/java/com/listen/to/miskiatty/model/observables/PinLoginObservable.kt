package com.listen.to.miskiatty.model.observables

import androidx.databinding.BaseObservable
import com.listen.to.miskiatty.model.DataValidation

class PinLoginObservable: BaseObservable() {
    fun pinLogin(pin: String): Boolean{
        return DataValidation().validatePinLogin(pin)
    }
}