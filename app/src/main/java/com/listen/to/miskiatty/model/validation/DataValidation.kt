package com.listen.to.miskiatty.model.validation

import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.model.provider.PreferenceProvider

class DataValidation {
    fun validatePin(tl_pin: TextInputLayout): Boolean{
        val appContext = tl_pin.context.applicationContext
        val pin = tl_pin.editText?.text.toString()
        val credential = PreferenceProvider(appContext).getPinLogin()

        return pin == credential
    }

    fun validatePinAndConfirmed(tl_pin: TextInputLayout, tl_confirmPin: TextInputLayout): Boolean {
        val pin = tl_pin.editText?.text.toString()
        val confirmPin = tl_confirmPin.editText?.text.toString()

        return (pin == confirmPin && pin.length == 6)
    }

}