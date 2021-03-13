package com.listen.to.miskiatty.model.validation

import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.model.enum.ErrorsEnum
import com.listen.to.miskiatty.model.provider.ErrorsStringProvider
import com.listen.to.miskiatty.model.provider.PreferenceProvider
import java.util.regex.Pattern

class DataValidation {

    private val errorsStringProvider = ErrorsStringProvider()

    fun validateEmail(tl_email: TextInputLayout): Boolean{
        val appContext = tl_email.context.applicationContext
        val email = tl_email.editText?.text.toString()
        val credential = PreferenceProvider(appContext).getEmailLogin()

        return email == credential
    }

    fun validatePassword(tl_password: TextInputLayout): Boolean{
        val appContext = tl_password.context.applicationContext
        val password = tl_password.editText?.text.toString()
        val credential = PreferenceProvider(appContext).getPasswordLogin()

        return password == credential
    }

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