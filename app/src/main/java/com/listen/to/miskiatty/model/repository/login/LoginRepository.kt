package com.listen.to.miskiatty.model.repository.login

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.model.provider.PreferenceProvider

interface LoginRepository {
    fun getEmailInputError(context: Context, email: String): String
    fun getPasswordInputError(context: Context, password: String):  String
}