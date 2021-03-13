package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import com.google.android.material.textfield.TextInputLayout

interface LoginRepository {
    fun getEmailInputError(context: Context, email: String): String
    fun getPasswordInputError(context: Context, password: String):  String
    fun emailValidation(tl_email: TextInputLayout): Boolean
    fun passwordValidation(tl_password: TextInputLayout): Boolean
}