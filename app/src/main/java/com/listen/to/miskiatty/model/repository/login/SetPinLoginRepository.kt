package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import com.google.android.material.textfield.TextInputLayout

interface SetPinLoginRepository {
    fun pinsValidation(tl_pin: TextInputLayout, tl_confirmPin: TextInputLayout):
            Boolean
    fun getPinsInputError(context: Context, pin: String, confrimPin: String):
            String
}