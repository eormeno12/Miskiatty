package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import com.google.android.material.textfield.TextInputLayout

interface PinLoginRepository {
    fun pinValidation(tl_pin: TextInputLayout): Boolean
    fun getPinInputError(context: Context, pin: String):  String
}